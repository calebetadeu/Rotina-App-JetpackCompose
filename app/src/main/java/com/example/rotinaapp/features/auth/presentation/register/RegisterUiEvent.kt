package com.example.rotinaapp.features.auth.presentation.register

sealed class RegisterUiEvent {
    data object Navigate : RegisterUiEvent()
}