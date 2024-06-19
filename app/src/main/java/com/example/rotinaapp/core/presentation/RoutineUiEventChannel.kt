package com.example.rotinaapp.core.presentation

import com.example.rotinaapp.core.presentation.util.UiEventSender
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object RoutineUiEventsChannel : UiEventSender {
    private val _routineUiEvent = Channel<RoutineUiEvent>()
    val routineUiEvent = _routineUiEvent.receiveAsFlow()

    override suspend fun sendEvent(event: RoutineUiEvent) {
        _routineUiEvent.send(event)
    }
}