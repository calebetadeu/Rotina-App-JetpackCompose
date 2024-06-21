package com.example.rotinaapp.features.auth.presentation.login

import com.example.rotinaapp.core.domain._util.EMPTY_STRING
import com.example.rotinaapp.core.presentation.util.UiText

data class LoginState(
    val email: String = String.EMPTY_STRING,
    val password: String = String.EMPTY_STRING,
    val isPasswordVisible: Boolean = false,
    val emailErrMsg: UiText? = null,
    val passwordErrMsg: UiText? = null,
    val isEmailChecked: Boolean = false,
    val successSendEmail:String = String.EMPTY_STRING,
    val isLoading: Boolean = false,
    val isLoadingSendEmail: Boolean = false,
    val isLoadingGoogle: Boolean = false,
    val isSigInSuccess: Boolean = false,
    val isSigInError: Boolean = false,


    )