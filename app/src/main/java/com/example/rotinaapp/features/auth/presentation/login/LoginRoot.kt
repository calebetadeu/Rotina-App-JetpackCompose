package com.example.rotinaapp.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.rotinaapp.features.auth.presentation.register.Register
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {


    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            if (action == LoginAction.NavigateToRegisterButtonClick) {
                navController.navigate(Register)
            }
            viewModel.onAction(action)
        },
    )


}

@Serializable
object Login