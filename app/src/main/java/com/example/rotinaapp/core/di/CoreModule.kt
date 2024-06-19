package com.example.rotinaapp.core.di

import com.example.rotinaapp.MainActivityViewModel
import com.example.rotinaapp.core.presentation.RoutineUiEventsChannel
import com.example.rotinaapp.core.presentation.util.UiEventSender
import com.example.rotinaapp.features.auth.domain.useCase.AuthenticateUseCase
import com.example.rotinaapp.features.task.presentation.home.TaskHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    presentation()
    domainModule()
}

private fun Module.domainModule() {
    singleOf(::AuthenticateUseCase)


}

private fun Module.presentation() {
    single<UiEventSender> { RoutineUiEventsChannel }
    viewModelOf(::MainActivityViewModel)

    viewModelOf(::TaskHomeViewModel)
}