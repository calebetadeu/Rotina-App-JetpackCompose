package com.example.rotinaapp.features.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rotinaapp.coreUi.LocalSpacing
import com.example.rotinaapp.features.auth.presentation._common.components.InputBasic
import com.example.rotinaapp.features.auth.presentation._common.components.InputPassword
import com.example.rotinaapp.ui.theme.RotinaAppTheme

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Criar conta")
            Form(
                userName = state.fullName,
                onUserNameChanged = {userName->
                    onAction(RegisterAction.OnUserNameChanged(userName))

                },
                onUserFocusChanged = {isFocus->
                    onAction(RegisterAction.OnUserNameFocusChanged(isFocus))

                },
                userNameErrMsg = state.userNameErrMsg?.asString(),
                userEmail = state.userEMail,
                onEmailFocusChanged = {isFocus->
                    onAction(RegisterAction.OnEmailFocusChanged(isFocus))

                },
                onUserEmailChanged = {userEMail->
                    onAction(RegisterAction.OnEmailChanged(userEMail))

                },
                emailErrMsg = state.emailErrMsg?.asString(),
                isUserNameChecked = state.isUserNameChecked,
                isEmailChecked = state.isEmailChecked,
                password = state.password,
                onPasswordChanged = {password->
                    onAction(RegisterAction.OnPasswordChanged(password))

                },
                onPasswordFocusChanged = {isFocus->
                    onAction(RegisterAction.OnPasswordFocusChanged(isFocus))

                },
                passwordErrMsg = state.passwordErrMsg?.asString(),
                isPasswordVisible = state.isPasswordVisible,
                confirmPassword = state.confirmPassword,
                onConfirmPasswordChanged = {confirmPassword->
                    onAction(RegisterAction.OnConfirmPasswordChanged(confirmPassword))

                },
                onConfirmPasswordFocusChanged = {isFocus->
                    onAction(RegisterAction.OnConfirmPasswordFocusChanged(isFocus))

                },
                confirmPasswordErrMsg = state.confirmPasswordErrMsg?.asString(),
                onVisibilityIconClicked = {
                    onAction(RegisterAction.OnChangePasswordVisibility)
                },
                isConfirmPasswordVisible = state.isConfirmPasswordVisible,

                onButtonClicked = {
                    onAction(RegisterAction.OnRegisterButtonClicked)
                }
            )

        }
    }

}

@Composable
fun Form(
    modifier: Modifier = Modifier,
    userName: String,
    onUserNameChanged: (String) -> Unit,
    onUserFocusChanged: (Boolean) -> Unit,
    userEmail: String,
    userNameErrMsg: String?,
    isUserNameChecked: Boolean,
    emailErrMsg: String?,
    onUserEmailChanged: (String) -> Unit,
    onEmailFocusChanged: (Boolean) -> Unit,
    isEmailChecked: Boolean,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onPasswordFocusChanged: (Boolean) -> Unit,
    passwordErrMsg: String?,
    isPasswordVisible: Boolean,
    onVisibilityIconClicked: () -> Unit,
    confirmPassword: String,
    isConfirmPasswordVisible: Boolean,
    onConfirmPasswordChanged: (String) -> Unit,
    onConfirmPasswordFocusChanged: (Boolean) -> Unit,
    confirmPasswordErrMsg: String?,
    onButtonClicked: () -> Unit,

) {
    //Local Spacing
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .padding(
                start = spacing.spaceLarge,
                top = spacing.spaceLarge,
                end = spacing.spaceLarge
            )
    ) {
        InputBasic(
            inputText = userName,
            onInputTextChanged = onUserNameChanged,
            label = "Nome Completo",
            leadingIcon = Icons.Default.CheckCircle,
            errorMessage = userNameErrMsg,
            isInputChecked = isUserNameChecked,
            onInputFocusChanged = onUserFocusChanged

        )
        InputBasic(
            inputText = userEmail,
            onInputTextChanged = onUserEmailChanged,
            onInputFocusChanged = onEmailFocusChanged,
            errorMessage = emailErrMsg,
            isInputChecked = isEmailChecked,
            label = "Email"
        )
        InputPassword(
            passwordText = password,
            onPasswordChanged = {
                onPasswordChanged(it)
            },
            label = "Senha",
            onPasswordFocusChanged = onPasswordFocusChanged,
            errorMessage = passwordErrMsg,
            isVisible = isPasswordVisible,
            onVisibilityIconClick = onVisibilityIconClicked
        )
        InputPassword(
            passwordText = confirmPassword,
            onPasswordChanged = {
                onConfirmPasswordChanged(it)
            },
            label = "Confirmar Senha",
            onPasswordFocusChanged = onConfirmPasswordFocusChanged,
            errorMessage = confirmPasswordErrMsg,
            isVisible = isConfirmPasswordVisible,
            onVisibilityIconClick = onVisibilityIconClicked
        )
        Button(
            onClick = onButtonClicked,
            modifier = Modifier
                .padding(
                    top = spacing.spaceLarge,
                )
                .fillMaxWidth(),
        ) {
            Text("Cadastrar")
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RotinaAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //RegisterScreen()
        }
    }
}