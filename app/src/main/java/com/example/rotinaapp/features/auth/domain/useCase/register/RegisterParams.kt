package com.example.rotinaapp.features.auth.domain.useCase.register


data class RegisterParams(
    val name:String,
    val email: String,
    val password: String
)
