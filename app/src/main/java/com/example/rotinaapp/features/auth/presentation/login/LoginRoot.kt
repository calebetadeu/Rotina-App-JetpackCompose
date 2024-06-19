package com.example.rotinaapp.features.auth.presentation.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.rotinaapp.features.auth.presentation.register.Register
import com.example.rotinaapp.features.task.presentation.home.Home
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel(),

    ) {


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.Navigate -> {
                    navController.navigate(
                        Home
                    ) {
                        popUpTo(Login) {
                            viewModel.user.value?.let {
                                inclusive = true
                            }
                        }
                    }

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
                    val singInResult = viewModel.loginWithGoogle(
                        intent = result.data ?: return@launch
                    )

                    viewModel.loginWithIntent()

                }
            }
        }
    )

    LoginScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                is LoginAction.NavigateToRegisterButtonClick -> {
                    navController.navigate(Register)
                }

                is LoginAction.OnLoginGoogle -> {
                    lifecycleScope.launch {
                        val sigInIntent = viewModel.loginWithIntent()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                intentSender = sigInIntent ?: return@launch
                            ).build()
                        )
                    }
                }

                else -> viewModel.onAction(action)
            }


        },
    )


}

@Serializable
object Login