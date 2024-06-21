package com.example.rotinaapp.features.auth.presentation.login

sealed class LoginAction {
    data class OnEmailChanged(val email: String) : LoginAction()
    data class OnEmailFocusChanged(val isFocus: Boolean) : LoginAction()
    data class OnPasswordChanged(val password: String) : LoginAction()
    data class OnPasswordFocusChanged(val isFocus: Boolean) : LoginAction()
    data class onEmailForgotPasswordChanged(val email: String) : LoginAction()
    data class onEmailForgotFocusChanged(val isFocus: Boolean) : LoginAction()
    data object OnChangePasswordVisibility : LoginAction()
    data object NavigateToRegisterButtonClick : LoginAction()
    data object OnLoginButtonClick : LoginAction()
    data object OnLoginGoogle : LoginAction()
    data object onForgotPasswordClick : LoginAction()
    data object onSendEmail : LoginAction()

}