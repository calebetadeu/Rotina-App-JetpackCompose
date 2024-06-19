package com.example.rotinaapp.core.presentation.util

import com.example.rotinaapp.core.presentation.RoutineUiEvent

interface UiEventSender{
    suspend fun sendEvent(event: RoutineUiEvent)
}