package com.example.rotinaapp.features.auth.domain.model

data class UserModel(
    val id: String,
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null

)