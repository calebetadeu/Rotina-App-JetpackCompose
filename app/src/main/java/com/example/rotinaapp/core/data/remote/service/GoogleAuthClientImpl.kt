package com.example.rotinaapp.core.data.remote.service

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.example.rotinaapp.core.domain._util.Result
import com.example.rotinaapp.core.domain.model.DataError
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginParams
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterParams
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthClientImpl(
    private val firebaseAuth: FirebaseAuth,
    private val oneTapClient: SignInClient,
    private val context: Context
) : GoogleAuthClient {
    private val auth = Firebase.auth
    override suspend fun signIn(loginParams: LoginParams): Result<UserModel, DataError.Network> {
        return try {
            val result =
                firebaseAuth.signInWithEmailAndPassword(loginParams.email, loginParams.password)
                    .await()
            val firebaseUser = result.user
            firebaseUser?.let {
                Result.Success(it.toAuthModel())
            } ?: Result.Error(DataError.Network.UNKNOWN, "Unknown error: User is null.")
        } catch (e: FirebaseAuthException) {
            Result.Error(e.toDataError(), e.message)
        } catch (e: Exception) {
            Log.d("FirebaseSource", e.message.toString())
            Result.Error(DataError.Network.UNKNOWN, e.message)
        }
    }

    override suspend fun signUp(registerParams: RegisterParams): Result<UserModel, DataError.Network> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(
                registerParams.email,
                registerParams.password
            ).await()
            val firebaseUser = result.user
            firebaseUser?.let {
                Result.Success(it.toAuthModel())
            } ?: Result.Error(DataError.Network.UNKNOWN, "Unknown error: User is null.")
        } catch (e: FirebaseAuthException) {
            Result.Error(e.toDataError(), e.message)
        } catch (e: Exception) {
            Log.d("FirebaseSource", e.message.toString())
            Result.Error(DataError.Network.UNKNOWN, e.message)
        }
    }

    override suspend fun isAuthenticated(): Result<Boolean, DataError.Network> {
        return try {
            val currentUser = firebaseAuth.currentUser
            Result.Success(currentUser != null)
        } catch (e: FirebaseAuthException) {
            Result.Error(e.toDataError(), e.message)
        } catch (e: Exception) {
            Log.d("FirebaseSource", e.message.toString())
            Result.Error(DataError.Network.UNKNOWN, e.message)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun signingWithGoogle(intent: Intent): Result<UserModel, DataError.Network> {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            Result.Success(
                UserModel(
                    id = user?.uid ?: "",
                    name = user?.displayName ?: "",
                    photoUrl = user?.photoUrl?.toString() ?: ""
                )
            )
        } catch (e: FirebaseAuthException) {
            Result.Error(e.toDataError(), e.message)
        } catch (e: Exception) {
            Log.d("FirebaseSource", e.message.toString())
            Result.Error(DataError.Network.UNKNOWN, e.message)
        }
    }

   override suspend fun signInWithIntent(): IntentSender {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null

        }
        return result?.pendingIntent?.intentSender ?: throw Exception()
    }


    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("500838488980-8eatvq99mko278e123bj7qr8ujjrsmlv.apps.googleusercontent.com")
                    .build()

            )
            .setAutoSelectEnabled(true)
            .build()

    }

    private fun FirebaseAuthException.toDataError(): DataError.Network {
        return when (this.errorCode) {
            "ERROR_INVALID_CUSTOM_TOKEN", "ERROR_CUSTOM_TOKEN_MISMATCH", "ERROR_INVALID_CREDENTIAL" -> DataError.Network.UNAUTHORIZED
            "ERROR_USER_DISABLED" -> DataError.Network.UNAUTHORIZED
            "ERROR_USER_NOT_FOUND", "ERROR_WRONG_PASSWORD" -> DataError.Network.BAD_REQUEST
            "ERROR_NETWORK_REQUEST_FAILED" -> DataError.Network.NO_INTERNET
            else -> DataError.Network.UNKNOWN
        }
    }

    private fun FirebaseUser.toAuthModel(): UserModel {
        return UserModel(
            id = this.uid ?: "",
            email = this.email ?: "",
            name = this.displayName ?: "",
            photoUrl = this.photoUrl?.toString() ?: ""
        )
    }
}