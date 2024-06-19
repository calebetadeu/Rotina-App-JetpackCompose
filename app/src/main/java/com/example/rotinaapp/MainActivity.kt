package com.example.rotinaapp

import RoutineNavHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.rotinaapp.core.presentation.RoutineUiEvent
import com.example.rotinaapp.core.presentation.RoutineUiEventsChannel
import com.example.rotinaapp.core.presentation.util.ObserveAsEvents
import com.example.rotinaapp.coreUi.RotinaAppTheme
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.isSplashFinishedFlow.value
        }
        enableEdgeToEdge()
        setContent {
            RotinaAppTheme(
                darkTheme = false
            ) {
                val snackbarHostState = remember { SnackbarHostState() }
                ObserveTaskyUiEvents(snackbarHostState)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        content = {
                            it
                            RoutineNavHost(
                                isAuthenticated = viewModel.isAuthenticated.collectAsState().value
                            )

                        }
                    )

                }
            }
        }
    }


    @Composable
    private fun ObserveTaskyUiEvents(snackbarHostState: SnackbarHostState) {
        ObserveAsEvents(flow = RoutineUiEventsChannel.routineUiEvent) { routineEvent ->
            when (routineEvent) {
                is RoutineUiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = routineEvent.message.asString(this@MainActivity),
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }

}
