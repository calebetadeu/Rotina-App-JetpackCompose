package com.example.rotinaapp.features.auth.presentation._common.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rotinaapp.coreUi.Primary

@Composable
fun ButtonBasic(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textButton: String,
    isLoading: Boolean,
    isEnabled: Boolean = true
) {
    val buttonColors = ButtonColors(
        containerColor = Primary,
        contentColor = Color.White,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.White
    )


    Button(
        onClick = onClick,
        colors = buttonColors,
        enabled =isEnabled,
        modifier = modifier
            .heightIn(min = 40.dp)

    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = Color.White,
            )
        } else {
            Text(textButton)
        }


    }
}