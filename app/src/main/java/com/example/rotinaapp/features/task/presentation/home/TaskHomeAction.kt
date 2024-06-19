package com.example.rotinaapp.features.task.presentation.home

sealed class TaskHomeAction{
    data object OnLogout : TaskHomeAction()
}