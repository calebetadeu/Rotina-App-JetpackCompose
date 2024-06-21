package com.example.rotinaapp.features.auth.domain.useCase.login

import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.data.AuthRepository
import com.example.rotinaapp.features.auth.domain.model.UserModel

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend  fun login(loginParams: LoginParams): Result<UserModel, DataError.Network> {
        return authRepository.signIn(loginParams)
    }
    suspend fun forgotPassword(email: String): Result<String, DataError.Network> {
        return authRepository.forgotPassword(email)
    }




}