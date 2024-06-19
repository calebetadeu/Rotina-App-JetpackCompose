package com.example.rotinaapp.core.data.remote.service

import android.content.Intent
import android.content.IntentSender
import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginParams
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterParams

interface GoogleAuthClient {
    suspend fun signIn(loginParams: LoginParams): Result<UserModel, DataError.Network>
    suspend fun signUp(registerParams: RegisterParams): Result<UserModel, DataError.Network>
    suspend fun isAuthenticated(): Result<Boolean, DataError.Network>
    suspend fun logout()
    suspend fun signingWithGoogle(intent: Intent): Result<UserModel, DataError.Network>
    suspend fun signInWithIntent(): IntentSender
}