package com.example.rotinaapp.features.auth.presentation.login

import com.example.rotinaapp.core.domain._util.EMPTY_STRING

data class LoginState(
    val email: String = String.EMPTY_STRING,
    val password: String = String.EMPTY_STRING,
    val isPasswordVisible: Boolean = false,
    val isLoading:Boolean = false,
    val isSigInSuccess:Boolean=false,
    val isSigInError:Boolean=false,


)