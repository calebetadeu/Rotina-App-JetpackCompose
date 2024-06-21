package com.example.rotinaapp.features.auth.presentation.register

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.rotinaapp.features.auth.domain.useCase.AuthenticateUseCase
import com.example.rotinaapp.features.task.presentation.home.Home
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    authenticateUseCase: AuthenticateUseCase = koinInject(),
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is RegisterUiEvent.Navigate -> {
                    navController.navigate(
                        Home
                    )
                }

            }
        }
    }
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val state = viewModel.state.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult = viewModel.registerWithGoogle(
                        intent = result.data ?: return@launch
                    )
                    authenticateUseCase.loginWithIntent()
                }

            }
        }
    )


    RegisterScreen(
        state = state.value,
        onAction = { action ->
            if (action == RegisterAction.NavigateBackToLogin) {
                navController.popBackStack()
            }
            if (action == RegisterAction.OnRegisterGoogleRegister){
                authenticateUseCase.launcherSingleGoogle(
                    lifecycleScope = lifecycleScope,
                    launcher = launcher
                )
            }
            viewModel.onAction(action)
        }
    )


}

@Serializable
object Register