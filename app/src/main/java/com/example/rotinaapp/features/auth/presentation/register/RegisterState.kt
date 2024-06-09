package com.example.rotinaapp.features.auth.presentation.register

import com.example.rotinaapp.core.domain._util.EMPTY_STRING
import com.example.rotinaapp.core.presentation.util.UiText

data class RegisterState(
    val fullName: String = String.EMPTY_STRING,
    val userNameErrMsg: UiText? = null,
    val userEMail: String = String.EMPTY_STRING,
    val emailErrMsg: UiText? = null,
    val password: String = String.EMPTY_STRING,
    val passwordErrMsg: UiText? = null,
    val isPasswordVisible: Boolean = false,
    val confirmPassword: String = String.EMPTY_STRING,
    val confirmPasswordErrMsg: UiText? = null,
    val isConfirmPasswordVisible: Boolean = false,
    val isUserNameChecked: Boolean = false,
    val isEmailChecked: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingGoogleRegister: Boolean = false,

)