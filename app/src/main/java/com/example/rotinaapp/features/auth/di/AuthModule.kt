package com.example.rotinaapp.features.auth.di

import android.content.Intent
import com.example.rotinaapp.MainActivity
import com.example.rotinaapp.features.auth.data.AuthRepositoryImpl
import com.example.rotinaapp.core.data.remote.service.GoogleAuthClient
import com.example.rotinaapp.core.data.remote.service.GoogleAuthClientImpl
import com.example.rotinaapp.features.auth.data.AuthRepository
import com.example.rotinaapp.features.auth.domain.useCase.ValidateConfirmPasswordUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidateEmailUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidatePasswordUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase
import com.example.rotinaapp.features.auth.domain.useCase.ValidateUserNameUseCase
import com.example.rotinaapp.features.auth.domain.useCase.login.LoginUseCase
import com.example.rotinaapp.features.auth.domain.useCase.register.RegisterUseCase
import com.example.rotinaapp.features.auth.presentation.login.LoginViewModel
import com.example.rotinaapp.features.auth.presentation.register.RegisterViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    presentationModule()
    domainModule()
    dataModule()
}

private fun Module.presentationModule() {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}

private fun Module.dataModule() {
    single { FirebaseAuth.getInstance() }
    single<Intent> {
        GoogleSignIn.getClient(
            androidContext(),
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        ).signInIntent
    }

    single<Intent> {
        Intent(androidContext(), MainActivity::class.java)
    }
    single<SignInClient> {
        Identity.getSignInClient(androidContext())
    }

    single<GoogleAuthClient> { GoogleAuthClientImpl(get(), get(), androidContext()) }
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}


private fun Module.domainModule() {
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidateUserNameUseCase)
    factoryOf(::ValidateRegistrationFieldsUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateConfirmPasswordUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::RegisterUseCase)
}