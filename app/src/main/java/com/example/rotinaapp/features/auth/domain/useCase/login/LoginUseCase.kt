package com.example.rotinaapp.features.auth.domain.useCase.login

import android.content.Intent
import android.content.IntentSender
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
   suspend fun loginWithGoogle(intent: Intent):Result<UserModel,DataError.Network>{
       return authRepository.signInWithGoogle(intent)

   }
    suspend fun loginWithIntent():IntentSender{
        return authRepository.sigInWithIntent()

    }



}