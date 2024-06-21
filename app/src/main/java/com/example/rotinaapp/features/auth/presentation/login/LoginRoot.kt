package com.example.rotinaapp.features.auth.presentation.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.rotinaapp.core.domain._util.EMPTY_STRING
import com.example.rotinaapp.features.auth.domain.useCase.AuthenticateUseCase
import com.example.rotinaapp.features.auth.presentation._common.ForgotPasswordSheet
import com.example.rotinaapp.features.auth.presentation.register.Register
import com.example.rotinaapp.features.task.presentation.home.Home
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRoot(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel(),
    authenticateUseCase: AuthenticateUseCase = koinInject()
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.Navigate -> {
                    navController.navigate(
                        Home
                    ) {
                        popUpTo(Login) {
                            inclusive = true
//                            viewModel.user.value?.let {
//                                inclusive = true
//                            }
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
                    viewModel.loginWithGoogle(
                        intent = result.data ?: return@launch
                    )
                    authenticateUseCase.loginWithIntent()
                }

            }
        }
    )

    if (showBottomSheet) {
        ForgotPasswordSheet(
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false },
            scope = scope,
            email = state.value.email,
            onEmailChanged = { email ->
                viewModel.onAction(LoginAction.onEmailForgotPasswordChanged(email))
            },
            successMessage = state.value.successSendEmail,
            errorMessage = state.value.emailErrMsg?.asString() ?: String.EMPTY_STRING,
            isLoadingSend = state.value.isLoadingSendEmail,
            onEmailFocusChanged = { isFocus ->  viewModel.onAction(LoginAction.OnEmailFocusChanged(isFocus)) },
            onSendEmail = {
                viewModel.onAction(LoginAction.onSendEmail)
            }
        )
    }

    LoginScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                is LoginAction.NavigateToRegisterButtonClick -> {
                    navController.navigate(Register)
                }

                is LoginAction.OnLoginGoogle -> {
                    authenticateUseCase.launcherSingleGoogle(
                        lifecycleScope = lifecycleScope,
                        launcher = launcher
                    )
                }

                is LoginAction.onForgotPasswordClick -> {
                    showBottomSheet = true
                }

                else -> viewModel.onAction(action)
            }


        },
    )


}

@Serializable
object Login