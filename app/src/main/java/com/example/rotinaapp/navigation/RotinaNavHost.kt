package com.example.rotinaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rotinaapp.features.auth.presentation.register.Register
import com.example.rotinaapp.features.auth.presentation.register.RegisterRoot

@Composable
internal fun RotinaNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Register) {
        composable<Register> {
            RegisterRoot(navController)
        }
    }
}

