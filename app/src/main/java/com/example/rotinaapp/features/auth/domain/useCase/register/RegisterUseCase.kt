package com.example.rotinaapp.features.auth.domain.useCase.register

import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.data.AuthRepository
import com.example.rotinaapp.features.auth.domain.model.UserModel

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(registerParams: RegisterParams): Result<UserModel, DataError.Network> {
        return authRepository.signUp(registerParams)
    }


}