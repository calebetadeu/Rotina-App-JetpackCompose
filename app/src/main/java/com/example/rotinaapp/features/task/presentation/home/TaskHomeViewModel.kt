package com.example.rotinaapp.features.task.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotinaapp.features.auth.data.AuthRepository
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.auth.presentation.login.LoginUiEvent
import com.example.rotinaapp.features.task.domain.HomeUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskHomeViewModel(
    private val authRepository: AuthRepository,
    private val homeUseCase: HomeUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            firebaseAuth.addAuthStateListener {
                if (it.currentUser == null) {
                    navigate()
                }
                homeUseCase.getUser(currentUser = it.currentUser)

            }
        }

    }

    fun onAction(action: TaskHomeAction) {
        when (action) {
            is TaskHomeAction.OnLogout -> {
                logout()
            }

        }
    }

    private fun navigate() {
        viewModelScope.launch {
            _uiEvent.send(LoginUiEvent.Navigate)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}