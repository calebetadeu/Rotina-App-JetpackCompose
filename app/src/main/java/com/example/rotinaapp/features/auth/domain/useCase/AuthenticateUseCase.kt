package com.example.rotinaapp.features.auth.domain.useCase

import android.content.Intent
import android.content.IntentSender
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.data.AuthRepository
import com.example.rotinaapp.features.auth.domain.model.UserModel
import kotlinx.coroutines.launch

class AuthenticateUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Boolean, DataError.Network> {
        return authRepository.isAuthenticated()
    }

    suspend fun loginWithGoogle(intent: Intent): Result<UserModel, DataError.Network> {

        return authRepository.signInWithGoogle(intent)

    }

    suspend fun loginWithIntent(): IntentSender {
        return authRepository.sigInWithIntent()

    }

    fun launcherSingleGoogle(
        lifecycleScope: LifecycleCoroutineScope,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) {
        lifecycleScope.launch {
            val sigInIntent = loginWithIntent()
            launcher.launch(
                IntentSenderRequest.Builder(
                    intentSender = sigInIntent ?: return@launch
                ).build()
            )
        }
    }
}