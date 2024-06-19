package com.example.rotinaapp.features.task.domain

import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow

class HomeUseCase(

) {
    fun getUser(currentUser: FirebaseUser?, user: MutableStateFlow<UserModel?>) {
        user.value = UserModel(
            id = currentUser?.uid ?: "",
            email = currentUser?.email ?: "",
            name = currentUser?.displayName ?: "",
            photoUrl = currentUser?.photoUrl?.toString() ?: ""
        )
    }
}
