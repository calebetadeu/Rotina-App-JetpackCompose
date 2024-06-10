package com.example.rotinaapp.features.auth.presentation._common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun FooterAuth(
    modifier: Modifier = Modifier,
    onNavigateBackToLogin: () -> Unit,
    questionText: String,
    textGoogle: String,
    isLoadingGoogle: Boolean,
    onButtonClickedGoogle: () -> Unit,
    textLink: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DividerAuth()
        Spacer(Modifier.height(2.dp))
        ButtonSocial(
            textButton = textGoogle,
            onClick = onButtonClickedGoogle,
            isLoading = isLoadingGoogle
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "$questionText?",

                )

            TextButton(
                onClick = {
                    onNavigateBackToLogin()
                }
            ) {
                Text(
                    textLink,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline
                )

            }
        }
    }
}