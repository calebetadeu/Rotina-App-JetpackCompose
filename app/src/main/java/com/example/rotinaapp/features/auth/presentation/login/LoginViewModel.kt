package com.example.rotinaapp.features.auth.presentation.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotinaapp.core.presentation.RoutineUiEvent
import com.example.rotinaapp.core.presentation.util.UiEventSender
import com.example.rotinaapp.core.presentation.util.UiText
import com.example.rotinaapp.core.presentation.util.UiText.DynamicString
import com.example.rotinaapp.features.auth.domain.model.InputValidationError
import com.example.rotinaapp.features.auth.domain.model.InputValidationErrors
import com.example.rotinaapp.features.auth.domain.model.InputValidationType
import com.example.rotinaapp.features.auth.domain.useCase.AuthenticateUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginParams
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val eventSender: UiEventSender,
    private val loginUseCase: LoginUseCase,
    private val authenticateUseCase: AuthenticateUseCase,
    private val validateRegistrationFieldsUseCase: ValidateRegistrationFieldsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnChangePasswordVisibility -> {
                _state.value = state.value.copy(isPasswordVisible = !state.value.isPasswordVisible)
            }

            is LoginAction.OnEmailChanged -> {
                clearErrors()
                _state.value = state.value.copy(email = action.email)
            }

            is LoginAction.OnPasswordChanged -> {
                clearErrors()
                _state.value = state.value.copy(password = action.password)
            }

            is LoginAction.onEmailForgotPasswordChanged -> {
                clearErrors()
                _state.value = state.value.copy(email = action.email)
            }

            is LoginAction.onSendEmail -> {
                validateInputs(
                    InputValidationType.EmailInputValidationType(state.value.email),
                    event = { sendEmail() }
                )

            }

            is LoginAction.OnLoginButtonClick -> {
                validateInputs(
                    InputValidationType.AllFieldsLoginValidationType(
                        state.value.email,
                        state.value.password,
                    ),
                    event = { login() }
                )
            }

            else -> Unit
        }
    }

    private fun validateInputs(
        inputValidationType: InputValidationType,
        event: () -> Unit
    ) {
        validateRegistrationFieldsUseCase(inputValidationType).fold(
            onError = { it, _ ->
                onValidationError(it, inputValidationType)
            }
        ) {
            onValidationSuccess(event)
        }
    }

    private fun onValidationSuccess(
        event: () -> Unit
    ) {
        _state.value = state.value.copy(isLoading = true)
        event()
    }

    private fun clearErrors() {
        _state.value = state.value.copy(
            emailErrMsg = null,
            passwordErrMsg = null,
        )
    }

    private fun onValidationError(
        failure: InputValidationErrors,
        inputValidationType: InputValidationType
    ) {
        val validationsErrors = (failure).validationErrors
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType ->
                _state.value =
                    state.value.copy(emailErrMsg = DynamicString("Formato inválido"))


            else -> {
                validationsErrors.forEach { inputValidationErrors ->
                    when (inputValidationErrors) {
                        InputValidationError.EmailValidatorError.Missing -> {
                            _state.value =
                                state.value.copy(emailErrMsg = DynamicString("Você precisa digitar um email"))
                        }

                        InputValidationError.EmailValidatorError.Format -> {
                            _state.value =
                                state.value.copy(emailErrMsg = DynamicString("Formato inválido"))
                        }

                        InputValidationError.PasswordValidatorError.Missing -> {
                            _state.value =
                                state.value.copy(passwordErrMsg = DynamicString("Você precisa digitar uma senha"))
                        }

                        else -> {}
                    }
                }
            }
        }
    }


//    private fun areFieldsFilled() =
//        state.value.email.isNotBlank() && state.value.password.isNotBlank()

    fun loginWithGoogle(intent: Intent) {
        _state.value = state.value.copy(isLoadingGoogle = true)
        viewModelScope.launch {
            authenticateUseCase.loginWithGoogle(
                intent
            ).fold(
                onSuccess = {
                    _uiEvent.send(LoginUiEvent.Navigate)
                    _state.value = state.value.copy(isLoadingGoogle = false)
                },
                onError = { dataError, errorMessage ->
                    val message = errorMessage ?: dataError.toString()
                    eventSender.sendEvent(
                        RoutineUiEvent.ShowSnackBar(
                            UiText.DynamicString(message)
                        )
                    )
                    _state.value = state.value.copy(isLoadingGoogle = false)

                },
            )
        }
    }

    private fun emailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
    }

    private fun login() {
        viewModelScope.launch {
            loginUseCase.login(
                LoginParams(
                    email = state.value.email,
                    password = state.value.password
                )
            ).fold(
                onError = { dataError, errorMessage ->
                    _state.value = state.value.copy(isLoading = false)
                    val message = errorMessage ?: dataError.toString()

                    eventSender.sendEvent(
                        RoutineUiEvent.ShowSnackBar(
                            UiText.DynamicString(message)
                        )
                    )
                },
                onSuccess = {
                    _uiEvent.send(LoginUiEvent.Navigate)
                    _state.value = state.value.copy(isLoading = false)
                }
            )
        }
    }

    private fun sendEmail() {
        _state.value = state.value.copy(isLoadingSendEmail = true)
        viewModelScope.launch {
            loginUseCase.forgotPassword(email = state.value.email).fold(
                onError = { dataError, errorMessage ->
                    _state.value = state.value.copy(isLoadingSendEmail = false)
                    val message = errorMessage ?: dataError.toString()
                    _state.value =
                        state.value.copy(emailErrMsg = DynamicString(message))
                },
                onSuccess = {
                    _state.value = state.value.copy(isLoadingSendEmail = false)
                    _state.value = state.value.copy(successSendEmail = it)
                })
        }
    }


}