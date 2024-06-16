package com.example.rotinaapp.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rotinaapp.features.auth.presentation.login.Login
import com.example.rotinaapp.features.auth.presentation.login.LoginRoot
import com.example.rotinaapp.features.auth.presentation.register.Register
import com.example.rotinaapp.features.auth.presentation.register.RegisterRoot
import com.example.rotinaapp.features.task.presentation.home.Home
import com.example.rotinaapp.features.task.presentation.home.HomeRoot

@Composable
internal fun RoutineNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Login>(
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
                )

            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )

            }
        ) {
            LoginRoot(navController = navController)
        }
        composable<Register>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
        ) {
            RegisterRoot(navController = navController)
        }
        composable<Home> {
            HomeRoot()
        }

    }
}

