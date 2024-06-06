package com.example.rotinaapp.features.auth.domain.model


sealed interface InputValidationError : com.example.rotinaapp.core.domain._util.Error {
    sealed interface EmailValidatorError : InputValidationError {
        data object Missing : EmailValidatorError
        data object Format : EmailValidatorError
    }

//    sealed interface PasswordValidatorError : InputValidationError {
//        data object Missing : PasswordValidatorError
//        data object Format : PasswordValidatorError
//        data object Length : PasswordValidatorError
//    }

    sealed interface UserNameValidatorError : InputValidationError {
        data object Missing : UserNameValidatorError
      //  data object Format : UserNameValidatorError
        data object Length : UserNameValidatorError
    }
}

data class InputValidationErrors(
    val validationErrors: List<InputValidationError>
) : com.example.rotinaapp.core.domain._util.Error