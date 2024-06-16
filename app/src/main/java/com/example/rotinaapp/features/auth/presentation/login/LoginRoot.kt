package com.example.rotinaapp.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.rotinaapp.features.auth.presentation.register.Register
import com.example.rotinaapp.features.task.presentation.home.Home
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
            if (action == LoginAction.OnLoginButtonClick) {
                navController.navigate(Home)
            }
            viewModel.onAction(action)
        },
    )


}

@Serializable
object Login