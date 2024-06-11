package com.example.rotinaapp.features.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rotinaapp.coreUi.LocalSpacing
import com.example.rotinaapp.features.auth.presentation._common.BaseAuthScreen
import com.example.rotinaapp.features.auth.presentation._common.components.ButtonBasic
import com.example.rotinaapp.features.auth.presentation._common.components.FooterAuth
import com.example.rotinaapp.features.auth.presentation._common.components.InputBasic
import com.example.rotinaapp.features.auth.presentation._common.components.InputPassword

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {


    BaseAuthScreen(
        topBarHeight = 60.dp,
        content = {


            Form(
                userName = state.fullName,
                onUserNameChanged = { userName ->
                    onAction(RegisterAction.OnUserNameChanged(userName))

                },
                onUserFocusChanged = { isFocus ->
                    onAction(RegisterAction.OnUserNameFocusChanged(isFocus))

                },
                userNameErrMsg = state.userNameErrMsg?.asString(),
                userEmail = state.userEMail,
                onEmailFocusChanged = { isFocus ->
                    onAction(RegisterAction.OnEmailFocusChanged(isFocus))

                },
                onUserEmailChanged = { userEMail ->
                    onAction(RegisterAction.OnEmailChanged(userEMail))

                },
                emailErrMsg = state.emailErrMsg?.asString(),
                isUserNameChecked = state.isUserNameChecked,
                isEmailChecked = state.isEmailChecked,
                password = state.password,
                onPasswordChanged = { password ->
                    onAction(RegisterAction.OnPasswordChanged(password))

                },
                onPasswordFocusChanged = { isFocus ->
                    onAction(RegisterAction.OnPasswordFocusChanged(isFocus))

                },
                passwordErrMsg = state.passwordErrMsg?.asString(),
                isPasswordVisible = state.isPasswordVisible,
                confirmPassword = state.confirmPassword,
                onConfirmPasswordChanged = { confirmPassword ->
                    onAction(RegisterAction.OnConfirmPasswordChanged(confirmPassword))

                },
                onConfirmPasswordFocusChanged = { isFocus ->
                    onAction(RegisterAction.OnConfirmPasswordFocusChanged(isFocus))

                },
                confirmPasswordErrMsg = state.confirmPasswordErrMsg?.asString(),
                onVisibilityIconClicked = {
                    onAction(RegisterAction.OnChangePasswordVisibility)
                },

                onConfirmVisibilityIconClicked = {
                    onAction(RegisterAction.OnConfirmPasswordVisibilityChanged)
                },
                isConfirmPasswordVisible = state.isConfirmPasswordVisible,

                onButtonClicked = {
                    onAction(RegisterAction.OnRegisterButtonClicked)
                },
                onButtonClickedGoogleRegister = {
                    onAction(RegisterAction.OnRegisterGoogleRegister)
                },
                isLoading = state.isLoading,
                isLoadingGoogle = state.isLoadingGoogleRegister,
                onNavigateBackToLogin = {
                    onAction(RegisterAction.NavigateBackToLogin)
                }
            )


        }
    )

}

@Composable
private fun Form(
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
    onConfirmVisibilityIconClicked: () -> Unit,
    confirmPassword: String,
    isConfirmPasswordVisible: Boolean,
    onConfirmPasswordChanged: (String) -> Unit,
    onConfirmPasswordFocusChanged: (Boolean) -> Unit,
    confirmPasswordErrMsg: String?,
    isLoading: Boolean,
    isLoadingGoogle: Boolean,
    onButtonClickedGoogleRegister: () -> Unit,
    onButtonClicked: () -> Unit,
    onNavigateBackToLogin: () -> Unit

) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = spacing.spaceLarge,
                top = spacing.spaceTiny,
                // end = spacing.spaceLarge
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Criar conta",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp
        )
        InputBasic(
            inputText = userName,
            onInputTextChanged = onUserNameChanged,
            label = "Nome Completo",
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
            onVisibilityIconClick = onConfirmVisibilityIconClicked
        )
        ButtonBasic(
            onClick = onButtonClicked,
            isLoading = isLoading,
            modifier = Modifier,
//                .padding(
//                    top = spacing.spaceLarge,
//                ),
            //.fillMaxWidth(),
            textButton = "Cadastrar"
        )

        FooterAuth(
            onNavigateBackToLogin = onNavigateBackToLogin,
            questionText = "Já tem uma conta",
            textLink = "Faça Login",
            isLoadingGoogle = isLoadingGoogle,
            onButtonClickedGoogle = onButtonClickedGoogleRegister,
            textGoogle = "Cadastre-se com a Google "
        )

    }

}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {

    RegisterScreen(
        state = RegisterState(),
        onAction = {}
    )


}
