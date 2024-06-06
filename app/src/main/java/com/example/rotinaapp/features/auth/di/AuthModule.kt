package com.example.rotinaapp.features.auth.di

import com.example.rotinaapp.features.auth.domain.useCase.ValidateConfirmPasswordUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidateEmailUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidatePasswordUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidateUserNameUseCase
import com.example.rotinaapp.features.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val authModule = module {
    presentationModule()
    domainModule()
}

private fun Module.presentationModule() {
    viewModelOf(::RegisterViewModel)
}

private fun Module.domainModule() {
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidateUserNameUseCase)
    factoryOf(::ValidateRegistrationFieldsUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateConfirmPasswordUseCase)

}