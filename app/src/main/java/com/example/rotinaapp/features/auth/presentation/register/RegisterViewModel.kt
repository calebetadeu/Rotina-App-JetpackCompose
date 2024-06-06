package com.example.rotinaapp.features.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.rotinaapp.core.presentation.util.UiText.DynamicString
import com.example.rotinaapp.features.auth.domain.model.InputValidationError
import com.example.rotinaapp.features.auth.domain.model.InputValidationErrors
import com.example.rotinaapp.features.auth.domain.model.InputValidationType
import com.example.rotinaapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase

class  RegisterViewModel(
    private val validateRegistrationFieldsUseCase: ValidateRegistrationFieldsUseCase,
//    private val registerUseCase:
//    private val eventSender: UiEventSender
): ViewModel() {
    var state by mutableStateOf(RegisterState())
    private set
    fun onAction(action: RegisterAction){
        when(action){
            is RegisterAction.OnUserNameChanged -> {
                state = state.copy(fullName = action.userName)
            }
            is RegisterAction.OnEmailChanged->{
                state = state.copy(userEMail = action.email)
            }

            is RegisterAction.OnEmailFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(emailErrMsg = null)
                } else {
                   // validateInputs(InputValidationType.EmailInputValidationType(state.email))
                }
            }
            RegisterAction.OnRegisterButtonClicked -> {
                validateInputs(
                    InputValidationType.AllFieldsRegisterValidationType(
                        state.fullName,
                        state.userEMail,
                    )
                )
            }
            is RegisterAction.OnUserNameFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(userNameErrMsg = null)
                } else {
                   // validateInputs(InputValidationType.UserNameInputValidationType(state.fullName))
                }
            }
        }

    }
    private fun validateInputs(
        inputValidationType: InputValidationType
    ) {
        validateRegistrationFieldsUseCase(inputValidationType).fold(
            onError = {
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
//                register()
            }
        }
    }
    private fun onValidationError(failure: InputValidationErrors, inputValidationType: InputValidationType) {
        val validationsErrors = (failure as InputValidationErrors).validationErrors
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
                                state.copy(userNameErrMsg = DynamicString("Você precisa digitar um nome de usuário") )//UiText.StringResource(R.string.error_mandatory_field))
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
                                state.copy(emailErrMsg = DynamicString( "Formato inválido"))
                        }
                    }
                }
            }
        }
    }


    private fun cleanErrors() {
        state = state.copy(
            emailErrMsg = null,
           // passwordErrMsg = null,
            userNameErrMsg = null,
        )
    }

}

