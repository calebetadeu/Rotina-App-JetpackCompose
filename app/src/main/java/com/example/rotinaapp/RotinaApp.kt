package com.example.rotinaapp

import android.app.Application
import com.example.rotinaapp.core.di.appModule
import com.example.rotinaapp.features.auth.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RotinaApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@RotinaApp)
            modules(appModule, authModule)
        }
    }
}