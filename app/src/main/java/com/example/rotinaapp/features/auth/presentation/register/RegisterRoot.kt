package com.example.rotinaapp.features.auth.presentation.register

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
) {

    RegisterScreen(
        state = viewModel.state,
        onAction = {action->
            viewModel.onAction(action)
        }
    )

}
@Serializable
object Register