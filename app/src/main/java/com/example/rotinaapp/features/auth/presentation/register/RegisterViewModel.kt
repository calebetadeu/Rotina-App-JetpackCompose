package com.example.rotinaapp.features.auth.presentation.register

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
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterParams
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(

    private val validateRegistrationFieldsUseCase: ValidateRegistrationFieldsUseCase,
    private val registerUseCase: RegisterUseCase,
    private val eventSender: UiEventSender,
    private val authenticateUseCase: AuthenticateUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<RegisterUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnUserNameChanged -> {
                _state.value = state.value.copy(fullName = action.userName)
            }

            is RegisterAction.OnEmailChanged -> {
                _state.value = state.value.copy(userEMail = action.email)
            }

            is RegisterAction.OnEmailFocusChanged -> {
                if (action.isFocus) {
                    _state.value = state.value.copy(emailErrMsg = null)
                } else {
                    // validateInputs(InputValidationType.EmailInputValidationType(state.email))
                }
            }

            RegisterAction.OnRegisterGoogleRegister -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(isLoadingGoogleRegister = true)
                }

            }

            RegisterAction.OnRegisterButtonClicked -> {
                validateInputs(
                    InputValidationType.AllFieldsRegisterValidationType(
                        _state.value.fullName,
                        _state.value.userEMail,
                        _state.value.password
                    )
                )

            }

            is RegisterAction.OnUserNameFocusChanged -> {
                if (action.isFocus) {
                    _state.value = state.value.copy(userNameErrMsg = null)
                } else {
                    validateInputs(InputValidationType.UserNameInputValidationType(state.value.fullName))
                }
            }

            RegisterAction.OnChangePasswordVisibility -> {
                _state.value = state.value.copy(isPasswordVisible = !state.value.isPasswordVisible)
            }

            is RegisterAction.OnPasswordChanged -> {
                _state.value = state.value.copy(password = action.password)

            }

            is RegisterAction.OnPasswordFocusChanged -> {
                if (action.isFocus) {
                    _state.value = state.value.copy(passwordErrMsg = null)
                }
            }

            is RegisterAction.OnConfirmPasswordChanged -> {
                _state.value = state.value.copy(confirmPassword = action.confirmPassword)
            }

            is RegisterAction.OnConfirmPasswordFocusChanged -> {
                if (action.isFocus) {
                    _state.value = state.value.copy(confirmPasswordErrMsg = null)
                }
            }

            is RegisterAction.OnConfirmPasswordVisibilityChanged -> {
                _state.value =
                    state.value.copy(isConfirmPasswordVisible = !state.value.isConfirmPasswordVisible)
            }

            is RegisterAction.OnPasswordVisibilityChanged -> {
                _state.value = state.value.copy(isPasswordVisible = action.isVisible)
            }

            else -> {}
        }

    }

    private fun validateInputs(
        inputValidationType: InputValidationType
    ) {
        validateRegistrationFieldsUseCase(inputValidationType).fold(
            onError = { it, _ ->
                onValidationError(it, inputValidationType)
            }
        ) {
            onValidationSuccess(inputValidationType)
        }
    }

    private fun onValidationSuccess(inputValidationType: InputValidationType) {
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType ->
                _state.value = state.value.copy(isEmailChecked = true)

            is InputValidationType.UserNameInputValidationType ->
                _state.value = state.value.copy(isUserNameChecked = true)

            else -> {
                cleanErrors()
                register()
            }
        }
    }

    private fun onValidationError(
        failure: InputValidationErrors,
        inputValidationType: InputValidationType
    ) {
        val validationsErrors = (failure).validationErrors
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType ->
                _state.value =
                    state.value.copy(isEmailChecked = false)

            is InputValidationType.UserNameInputValidationType ->
                _state.value =
                    state.value.copy(isUserNameChecked = false)

            else -> {
                cleanErrors()
                validationsErrors.forEach { inputValidationErrors ->
                    when (inputValidationErrors) {
                        InputValidationError.UserNameValidatorError.Missing -> {
                            _state.value =
                                state.value.copy(userNameErrMsg = DynamicString("Você precisa digitar um nome de usuário"))//UiText.StringResource(R.string.error_mandatory_field))
                        }

                        InputValidationError.UserNameValidatorError.Length -> {
                            _state.value =
                                state.value.copy(userNameErrMsg = DynamicString("Nome com tamanho inválido"))
                        }

                        InputValidationError.EmailValidatorError.Missing -> {
                            _state.value =
                                state.value.copy(emailErrMsg = DynamicString("Você precisa digitar um email"))
                        }

                        InputValidationError.EmailValidatorError.Format -> {
                            _state.value =
                                state.value.copy(emailErrMsg = DynamicString("Formato inválido"))
                        }

                        InputValidationError.PasswordValidatorError.Format -> {
                            _state.value =
                                state.value.copy(passwordErrMsg = DynamicString("Senha com formato inválido"))
                        }

                        InputValidationError.PasswordValidatorError.Length -> {
                            _state.value =
                                state.value.copy(passwordErrMsg = DynamicString("Senha deve conter no minimo 4 digitos"))
                        }

                        InputValidationError.PasswordValidatorError.Missing -> {
                            _state.value =
                                state.value.copy(passwordErrMsg = DynamicString("Você precisa digitar uma senha"))
                        }

                        InputValidationError.PasswordConfirmValidatorError.SamePassword -> {
                            _state.value =
                                state.value.copy(confirmPasswordErrMsg = DynamicString("As senhas devem ser iguais"))
                        }
                    }
                }
            }
        }
    }

    fun registerWithGoogle(intent: Intent) {
        _state.value = state.value.copy(isLoadingGoogleRegister = true)
        viewModelScope.launch {
            authenticateUseCase.loginWithGoogle(
                intent
            ).fold(
                onSuccess = {
                    _uiEvent.send(RegisterUiEvent.Navigate)
                    _state.value = state.value.copy(isLoadingGoogleRegister = true)
                },
                onError = { dataError, errorMessage ->
                    val message = errorMessage ?: dataError.toString()
                    eventSender.sendEvent(
                        RoutineUiEvent.ShowSnackBar(
                            UiText.DynamicString(message)
                        )
                    )
                    _state.value = state.value.copy(isLoadingGoogleRegister = false)

                },
            )
        }
    }

    private fun register() {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch {
            registerUseCase(
                RegisterParams(
                    name = state.value.fullName,
                    email = state.value.userEMail,
                    password = _state.value.password
                )
            ).fold(
                onError = { dataError, errorMessage ->
                    _state.value = state.value.copy(isLoading = false)
                    val message = errorMessage ?: dataError.toString()
                    eventSender.sendEvent(
                        RoutineUiEvent.ShowSnackBar(
                            DynamicString(message)
                        )
                    )
                },
                onSuccess = {
                    _state.value = state.value.copy(isLoading = false)

                    _uiEvent.send(RegisterUiEvent.Navigate)
                }
            )
        }

    }

    private fun cleanErrors() {
        _state.value = state.value.copy(
            emailErrMsg = null,
            passwordErrMsg = null,
            userNameErrMsg = null,
        )
    }

}

