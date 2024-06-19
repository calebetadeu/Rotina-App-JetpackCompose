package com.example.rotinaapp.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rotinaapp.coreUi.Background
import com.example.rotinaapp.coreUi.LocalSpacing
import com.example.rotinaapp.features.auth.presentation._common.BaseAuthScreen
import com.example.rotinaapp.features.auth.presentation._common.components.ButtonBasic
import com.example.rotinaapp.features.auth.presentation._common.components.FooterAuth
import com.example.rotinaapp.features.auth.presentation._common.components.InputBasic
import com.example.rotinaapp.features.auth.presentation._common.components.InputPassword

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {

    BaseAuthScreen(topBarHeight = 150.dp) {
        FormLogin(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            email = state.email,
            onEmailChanged = { email ->
                onAction(LoginAction.OnEmailChanged(email))
            },
            password = state.password,
            onPasswordChanged = { password ->
                onAction(LoginAction.OnPasswordChanged(password))
            },
            isPasswordVisible = state.isPasswordVisible,
            onVisibilityIconClicked = {
                onAction(LoginAction.OnChangePasswordVisibility)
            },
            onLoginButtonClicked = {
                onAction(LoginAction.OnLoginButtonClick)
            },
            onNavigateToRegister = {
                onAction(LoginAction.NavigateToRegisterButtonClick)
            },
            isLoadingGoogle = false,
            onButtonClickedGoogleRegister = {
                onAction(LoginAction.OnLoginGoogle)
            },
            isLoadingLogin = state.isLoading
        )
    }

}

@Composable
fun FormLogin(
    modifier: Modifier,
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityIconClicked: () -> Unit,
    onNavigateToRegister: () -> Unit,
    isLoadingLogin: Boolean,
    isLoadingGoogle: Boolean,
    onButtonClickedGoogleRegister: () -> Unit,
    onLoginButtonClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            "Login",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp
        )
        InputBasic(
            label = "Email",
            inputText = email,
            onInputTextChanged = { onEmailChanged(it) },
        )
        InputPassword(
            passwordText = password,
            onPasswordChanged = { onPasswordChanged(it) },
            isVisible = isPasswordVisible,
            onVisibilityIconClick = onVisibilityIconClicked,
            label = "Senha"
        )
        TextButton(
            onClick = {
            }
        ) {
            Text("Esqueci minha senha", textDecoration = TextDecoration.Underline)
        }
        ButtonBasic(
            onClick = onLoginButtonClicked,
            textButton = "Entrar",
            isLoading = isLoadingLogin,
            modifier = Modifier.padding(top = spacing.spaceLarge)
        )

        FooterAuth(
            onNavigateBackToLogin = onNavigateToRegister,
            questionText = "Ainda não tem uma conta",
            textLink = "Cadastre-se",
            isLoadingGoogle = isLoadingGoogle,
            onButtonClickedGoogle = onButtonClickedGoogleRegister,
            textGoogle = "Login com a Google"
        )

    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(state = LoginState()) {

    }

}