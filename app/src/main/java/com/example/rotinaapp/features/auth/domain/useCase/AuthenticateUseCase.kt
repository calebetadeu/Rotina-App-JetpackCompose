package com.example.rotinaapp.features.auth.domain.useCase

import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.data.AuthRepository

class AuthenticateUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Boolean, DataError.Network> {
        return authRepository.isAuthenticated()
    }
}