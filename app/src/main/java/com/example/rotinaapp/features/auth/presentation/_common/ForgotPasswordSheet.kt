package com.example.rotinaapp.features.auth.presentation._common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rotinaapp.core.domain._util.EMPTY_STRING
import com.example.rotinaapp.coreUi.Background
import com.example.rotinaapp.coreUi.LocalSpacing
import com.example.rotinaapp.features.auth.presentation._common.components.ButtonBasic
import com.example.rotinaapp.features.auth.presentation._common.components.InputBasic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ForgotPasswordSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSendEmail: () -> Unit,
    isLoadingSend: Boolean,
    successMessage: String = String.EMPTY_STRING,
    onEmailChanged: (String) -> Unit,
    onEmailFocusChanged: (Boolean) -> Unit,
    email: String,
    errorMessage: String,
    scope: CoroutineScope,
    sheetState: SheetState,
) {
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2000) // Simulating a 3 seconds loading task
        isLoading = false
    }

    ModalBottomSheet(
        modifier = modifier.fillMaxSize(),
        containerColor = Background,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        ForgotPasswordContent(
            onSendEmail = onSendEmail,
            onEmailChanged = onEmailChanged,
            email = email,
            isLoading = isLoading,
            errorMessage = errorMessage,
            scope = scope,
            successMessage = successMessage,
            isLoadingSend = isLoadingSend,
            onEmailFocusChanged = onEmailFocusChanged
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ForgotPasswordContent(
    onSendEmail: () -> Unit,
    successMessage: String = String.EMPTY_STRING,
    onEmailChanged: (String) -> Unit,
    onEmailFocusChanged: (Boolean) -> Unit,
    email: String,
    isLoading: Boolean,
    isLoadingSend: Boolean,
    errorMessage: String,
    scope: CoroutineScope
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = spacing.spaceLarge,
                top = spacing.spaceTiny,
                end = spacing.spaceLarge,
                bottom = spacing.spaceLarge
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (successMessage.isNotEmpty()) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedContent(
                    contentAlignment = Alignment.Center,
                    targetState = isLoading,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(600)) with fadeOut(
                            animationSpec = tween(600)
                        )
                    }, label = ""
                ) { targetState ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            //modifier = modifier,
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            //verticalArrangement = Arrangement.Center
                        ) {
                            if (targetState) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .fillMaxWidth(),
                                    color = Color.Green,
                                    strokeWidth = 4.dp
                                )
                            } else {
                                val maxProgress = 5
                                val currentProgress = 5
                                val progress by remember {
                                    mutableFloatStateOf(currentProgress / maxProgress.toFloat())
                                }
                                val animateProgress by animateFloatAsState(
                                    progress,
                                    animationSpec = tween(500, 500, FastOutSlowInEasing),
                                    label = "animation-progress"
                                )

                                Box {
                                    CircularProgressIndicator(
                                        progress = { animateProgress },
                                        modifier = Modifier.size(80.dp),
                                        strokeWidth = 4.dp,
                                        strokeCap = StrokeCap.Round,
                                        color = Green
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Checkmark",
                                        tint = Color.Green,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .padding(12.dp)
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                                Text("Email Enviado", fontSize = 21.sp)

                            }
                        }
                    }
                }


            }


        } else {
            Text("Esqueceu sua senha", fontSize = 21.sp)

            Spacer(Modifier.height(12.dp))

            Text(
                "Sem problemas! Digite seu endereço de e-mail abaixo e enviaremos um código para redefinir sua senha.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))
            InputBasic(
                label = "Email",
                inputText = email,
                onInputFocusChanged = {
                    onEmailFocusChanged(it)
                },
                onInputTextChanged = {
                    onEmailChanged(it)
                },
                errorMessage = errorMessage
            )
            Spacer(Modifier.height(12.dp))

            ButtonBasic(
                onClick = {
                    scope.launch {
                        onSendEmail()
//                    if (!sheetState.isVisible) {
//                        onDismissRequest()
//                    }
                    }
                },
                textButton = "Enviar", isEnabled = true, isLoading = isLoadingSend
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ForgotPasswordSheetPreview() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .padding(vertical = 40.dp)
    ) {

        Button(onClick = { showBottomSheet = true }) {
            Text("Abrir Bottom Sheet")

        }

    }


}