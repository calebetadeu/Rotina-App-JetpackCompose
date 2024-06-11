package com.example.rotinaapp.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    navController: NavController
) {


    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            if(action == RegisterAction.NavigateBackToLogin){
                navController.popBackStack()
            }
            viewModel.onAction(action)
        }
    )


}

@Serializable
object Register