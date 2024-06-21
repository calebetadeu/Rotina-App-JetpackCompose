package com.example.rotinaapp.features.auth.presentation.login

sealed class LoginUiEvent {
    data object Navigate : LoginUiEvent()

}