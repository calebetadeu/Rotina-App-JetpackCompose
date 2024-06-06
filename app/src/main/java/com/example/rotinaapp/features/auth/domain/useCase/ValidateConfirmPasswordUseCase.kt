package com.example.rotinaapp.features.auth.domain.useCase

import com.example.rotinaapp.features.auth.domain.model.InputValidationError.PasswordConfirmValidatorError

class ValidateConfirmPasswordUseCase {
    operator fun invoke(confirmPassword: String, password: String): PasswordConfirmValidatorError? {
        return if (confirmPassword != password) {
           PasswordConfirmValidatorError.SamePassword
        } else {
            null
        }
    }
}