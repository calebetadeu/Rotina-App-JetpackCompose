package com.example.rotinaapp.features.auth.presentation.register

sealed class RegisterAction {
    data class OnUserNameChanged(val userName: String) : RegisterAction()
    data class OnUserNameFocusChanged(val isFocus: Boolean) : RegisterAction()
    data class OnEmailChanged(val email: String) : RegisterAction()
    data class OnEmailFocusChanged(val isFocus: Boolean) : RegisterAction()
    data class OnPasswordChanged(val password: String) : RegisterAction()
    data class OnPasswordFocusChanged(val isFocus: Boolean) : RegisterAction()
    data object OnChangePasswordVisibility : RegisterAction()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterAction()
    data class OnConfirmPasswordFocusChanged(val isFocus: Boolean) : RegisterAction()
    data object OnConfirmPasswordVisibilityChanged : RegisterAction()
    data class OnPasswordVisibilityChanged(val isVisible: Boolean) : RegisterAction()

 //   data object NavigateBackToLogin : RegisterAction()
    data object OnRegisterButtonClicked : RegisterAction()
}