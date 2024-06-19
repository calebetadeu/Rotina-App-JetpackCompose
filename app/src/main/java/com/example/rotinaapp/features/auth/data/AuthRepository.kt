package com.example.rotinaapp.features.auth.data

import android.content.Intent
import android.content.IntentSender
import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginParams
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterParams


interface AuthRepository {
    suspend fun signIn(loginParams: LoginParams): Result<UserModel, DataError.Network>
    suspend fun signUp(registerParams: RegisterParams): Result<UserModel, DataError.Network>
    suspend fun signInWithGoogle(intent: Intent):Result<UserModel,DataError.Network>
    suspend fun sigInWithIntent ():IntentSender
    suspend fun isAuthenticated():Result< Boolean,DataError.Network>
    suspend fun logout()
    

}