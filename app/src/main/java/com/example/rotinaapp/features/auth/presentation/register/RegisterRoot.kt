package com.example.rotinaapp.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.rotinaapp.features.task.presentation.home.Home
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
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

    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            if (action == RegisterAction.NavigateBackToLogin) {
                navController.popBackStack()
            }
            viewModel.onAction(action)
        }
    )


}

@Serializable
object Register