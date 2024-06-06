package com.example.rotinaapp.features.auth.domain.useCase

import com.example.rotinaapp.features.auth.domain.model.InputValidationError.EmailValidatorError


class ValidateEmailUseCase {
    operator fun invoke(email: String): EmailValidatorError? =
        when {
            email.isBlank() -> EmailValidatorError.Missing
            !email.matches(EMAIL_REGEX.toRegex()) -> EmailValidatorError.Format
            else -> null
        }
    companion object {
        const val EMAIL_REGEX = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"
    }
}