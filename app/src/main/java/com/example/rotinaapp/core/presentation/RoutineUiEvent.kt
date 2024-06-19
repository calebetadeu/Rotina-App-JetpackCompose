package com.example.rotinaapp.core.presentation

import com.example.rotinaapp.core.presentation.util.UiText

sealed interface RoutineUiEvent {
    data class ShowSnackBar(val message: UiText) : RoutineUiEvent
}