package com.example.rotinaapp.features.task.di

import com.example.rotinaapp.features.task.domain.HomeUseCase
import com.example.rotinaapp.features.task.presentation.home.TaskHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeModule = module {
    domainModule()
    presentationModule()
}

private fun Module.domainModule() {
    factoryOf(::HomeUseCase)
}

private fun Module.presentationModule() {
    viewModelOf(::TaskHomeViewModel)
}
