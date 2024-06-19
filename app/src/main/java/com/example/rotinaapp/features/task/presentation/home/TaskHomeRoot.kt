@file:JvmName("TaskHomeViewModelKt")

package com.example.rotinaapp.features.task.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.rotinaapp.features.auth.presentation.login.Login
import com.example.rotinaapp.features.auth.presentation.login.LoginUiEvent
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    viewModel: TaskHomeViewModel = koinViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.Navigate -> {
                    navController.navigate(
                        Login
                    )
                }
            }
        }
    }
    HomeScreen(
        onAction = {
            viewModel.onAction(it)
        },
        user = viewModel.user.collectAsState().value
    )
}

@Serializable
object Home