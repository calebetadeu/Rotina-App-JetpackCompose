package com.example.rotinaapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotinaapp.features.auth.domain.useCase.AuthenticateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val authenticateUseCase: AuthenticateUseCase
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    private val isSplashFinished = MutableStateFlow<Boolean>(false)
    val isSplashFinishedFlow = isSplashFinished.asStateFlow()


    init {
        authenticate()
    }

    private fun authenticate() {
        isSplashFinished.value = true
        viewModelScope.launch {
            authenticateUseCase().fold(
                onSuccess = {
                    _isAuthenticated.value = it
                    isSplashFinished.value = true
                },
                onError = { _, _ ->
                    _isAuthenticated.value = false
                    isSplashFinished.value = true
                }

            )
        }
    }

}