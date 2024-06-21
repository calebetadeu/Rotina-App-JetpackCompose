package com.example.rotinaapp.features.auth.data

import android.content.Intent
import android.content.IntentSender
import com.example.rotinaapp.core.data.remote.service.GoogleAuthClient
import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginParams
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterParams

class AuthRepositoryImpl(
    private val authRemote: GoogleAuthClient,

    ) : AuthRepository {


    override suspend fun signIn(loginParams: LoginParams): Result<UserModel, DataError.Network> {
        return authRemote.signIn(loginParams)
    }

    override suspend fun signUp(registerParams: RegisterParams): Result<UserModel, DataError.Network> {
        return authRemote.signUp(registerParams)
    }

    override suspend fun signInWithGoogle(intent: Intent): Result<UserModel, DataError.Network> {
        return authRemote.signingWithGoogle(intent)
    }

    override suspend fun sigInWithIntent(): IntentSender {
        return authRemote.signInWithIntent()
    }

    override suspend fun isAuthenticated(): Result<Boolean, DataError.Network> {
        return authRemote.isAuthenticated()
    }

    override suspend fun logout() {
        return authRemote.logout()
    }

    override suspend fun forgotPassword(email: String): Result<String, DataError.Network> {
        return authRemote.forgotPassword(email)
    }


}