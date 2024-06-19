package com.example.rotinaapp.features.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotinaapp.core.presentation.RoutineUiEvent
import com.example.rotinaapp.core.presentation.util.UiEventSender
import com.example.rotinaapp.core.presentation.util.UiText.DynamicString
import com.example.rotinaapp.features.auth.domain.model.InputValidationError
import com.example.rotinaapp.features.auth.domain.model.InputValidationErrors
import com.example.rotinaapp.features.auth.domain.model.InputValidationType
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterParams
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validateRegistrationFieldsUseCase: ValidateRegistrationFieldsUseCase,
    private val registerUseCase: RegisterUseCase,
    private val eventSender: UiEventSender
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set
    private val _uiEvent = Channel<RegisterUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _user = MutableStateFlow<UserModel?>(null)


    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnUserNameChanged -> {
                state = state.copy(fullName = action.userName)
            }

            is RegisterAction.OnEmailChanged -> {
                state = state.copy(userEMail = action.email)
            }

            is RegisterAction.OnEmailFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(emailErrMsg = null)
                } else {
                    // validateInputs(InputValidationType.EmailInputValidationType(state.email))
                }
            }

            RegisterAction.OnRegisterGoogleRegister -> {
                viewModelScope.launch {
                    state = state.copy(isLoadingGoogleRegister = true)
                }

            }

            RegisterAction.OnRegisterButtonClicked -> {
                validateInputs(
                    InputValidationType.AllFieldsRegisterValidationType(
                        state.fullName,
                        state.userEMail,
                        state.password
                    )
                )

            }

            is RegisterAction.OnUserNameFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(userNameErrMsg = null)
                } else {
                    validateInputs(InputValidationType.UserNameInputValidationType(state.fullName))
                }
            }

            RegisterAction.OnChangePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            is RegisterAction.OnPasswordChanged -> {
                state = state.copy(password = action.password)

            }

            is RegisterAction.OnPasswordFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(passwordErrMsg = null)
                }
            }

            is RegisterAction.OnConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = action.confirmPassword)
            }

            is RegisterAction.OnConfirmPasswordFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(confirmPasswordErrMsg = null)
                }
            }

            is RegisterAction.OnConfirmPasswordVisibilityChanged -> {
                state = state.copy(isConfirmPasswordVisible = !state.isConfirmPasswordVisible)
            }

            is RegisterAction.OnPasswordVisibilityChanged -> {
                state = state.copy(isPasswordVisible = action.isVisible)
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
                state = state.copy(isEmailChecked = true)

            is InputValidationType.UserNameInputValidationType ->
                state = state.copy(isUserNameChecked = true)

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
                state =
                    state.copy(isEmailChecked = false)

            is InputValidationType.UserNameInputValidationType ->
                state =
                    state.copy(isUserNameChecked = false)

            else -> {
                cleanErrors()
                validationsErrors.forEach { inputValidationErrors ->
                    when (inputValidationErrors) {
                        InputValidationError.UserNameValidatorError.Missing -> {
                            state =
                                state.copy(userNameErrMsg = DynamicString("Você precisa digitar um nome de usuário"))//UiText.StringResource(R.string.error_mandatory_field))
                        }

                        InputValidationError.UserNameValidatorError.Length -> {
                            state =
                                state.copy(userNameErrMsg = DynamicString("Nome com tamanho inválido"))
                        }

                        InputValidationError.EmailValidatorError.Missing -> {
                            state =
                                state.copy(emailErrMsg = DynamicString("Você precisa digitar um email"))
                        }

                        InputValidationError.EmailValidatorError.Format -> {
                            state =
                                state.copy(emailErrMsg = DynamicString("Formato inválido"))
                        }

                        InputValidationError.PasswordValidatorError.Format -> {
                            state =
                                state.copy(passwordErrMsg = DynamicString("Senha com formato inválido"))
                        }

                        InputValidationError.PasswordValidatorError.Length -> {
                            state =
                                state.copy(passwordErrMsg = DynamicString("Senha deve conter no minimo 4 digitos"))
                        }

                        InputValidationError.PasswordValidatorError.Missing -> {
                            state =
                                state.copy(passwordErrMsg = DynamicString("Você precisa digitar uma senha"))
                        }

                        InputValidationError.PasswordConfirmValidatorError.SamePassword -> {
                            state =
                                state.copy(confirmPasswordErrMsg = DynamicString("As senhas devem ser iguais"))
                        }
                    }
                }
            }
        }
    }

    private fun register() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            registerUseCase(
                RegisterParams(
                    name = state.fullName,
                    email = state.userEMail,
                    password = state.password
                )
            ).fold(
                onError = { dataError, errorMessage ->
                    state = state.copy(isLoading = false)
                    val message = errorMessage ?: dataError.toString()
                    eventSender.sendEvent(
                        RoutineUiEvent.ShowSnackBar(
                            DynamicString(message)
                        )
                    )
                },
                onSuccess = {
                    state = state.copy(isLoading = false)
                    _user.value = it
                    _uiEvent.send(RegisterUiEvent.Navigate)
                }
            )
        }

    }

    private fun cleanErrors() {
        state = state.copy(
            emailErrMsg = null,
            passwordErrMsg = null,
            userNameErrMsg = null,
        )
    }

}

