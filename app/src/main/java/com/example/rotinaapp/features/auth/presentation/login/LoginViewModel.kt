package com.example.rotinaapp.features.auth.presentation.login

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotinaapp.core.presentation.RoutineUiEvent
import com.example.rotinaapp.core.presentation.util.UiEventSender
import com.example.rotinaapp.core.presentation.util.UiText
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginParams
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val eventSender: UiEventSender,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val _user = MutableStateFlow<UserModel?>(null)
    val user = _user.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnChangePasswordVisibility -> {
                _state.value = state.value.copy(isPasswordVisible = !state.value.isPasswordVisible)
            }

            is LoginAction.OnEmailChanged -> {
                _state.value = state.value.copy(email = action.email)
            }

            is LoginAction.OnPasswordChanged -> {
                _state.value = state.value.copy(password = action.password)
            }

//            is LoginAction.OnLoginGoogle -> {
//                _state.value = state.value.copy(isLoading = true)
//                loginWithGoogle(intent =)
//            }


            is LoginAction.OnLoginButtonClick -> {
                _state.value = state.value.copy(isLoading = true)
                if (areFieldsFilled()) {
                    login()
                } else {
                    viewModelScope.launch {
                        eventSender.sendEvent(
                            RoutineUiEvent.ShowSnackBar(
                                UiText.DynamicString(
                                    "Os Campos devem ser preenchidos"
                                )
                            )
                        )
                    }
                }
            }

            else -> Unit
        }
    }

    private fun areFieldsFilled() =
        state.value.email.isNotBlank() && state.value.password.isNotBlank()

    fun loginWithGoogle(intent: Intent) {
        viewModelScope.launch {
            loginUseCase.loginWithGoogle(
                intent
            ).fold(
                onSuccess = {
                    _user.value = it
                    _uiEvent.send(LoginUiEvent.Navigate)
                },
                onError = { dataError, errorMessage ->
                    _state.value = state.value.copy(isLoading = false)
                    val message = errorMessage ?: dataError.toString()
                    eventSender.sendEvent(
                        RoutineUiEvent.ShowSnackBar(
                            UiText.DynamicString(message)
                        )
                    )
                },
            )
        }
    }

    suspend fun loginWithIntent():IntentSender {
        return loginUseCase.loginWithIntent()
    }


    private fun login() {
        viewModelScope.launch {
            loginUseCase.login(
                LoginParams(
                    email = state.value.email,
                    password = state.value.password
                )
            ).fold(
                onError = { dataError, errorMessage ->
                    _state.value = state.value.copy(isLoading = false)
                    val message = errorMessage ?: dataError.toString()
                    eventSender.sendEvent(
                        RoutineUiEvent.ShowSnackBar(
                            UiText.DynamicString(message)
                        )
                    )
                },
                onSuccess = {
                    _state.value = state.value.copy(isLoading = false)
                    _user.value = it
                    _uiEvent.send(LoginUiEvent.Navigate)
                }
            )
        }
    }


}