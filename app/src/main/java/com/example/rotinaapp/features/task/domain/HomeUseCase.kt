package com.example.rotinaapp.features.task.domain

import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow

class HomeUseCase(
    private val user: MutableStateFlow<UserModel?>
) {
    fun getUser(currentUser: FirebaseUser?) {
        user.value = UserModel(
            id = currentUser?.uid ?: "",
            email = currentUser?.email ?: "",
            name = currentUser?.displayName ?: "",
            photoUrl = currentUser?.photoUrl?.toString() ?: ""
        )
    }
}
