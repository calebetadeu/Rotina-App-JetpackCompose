package com.example.rotinaapp.features.auth.presentation._common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rotinaapp.coreUi.RotinaAppTheme

@Composable
fun DividerAuth() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f)) // Left divider
        Text(
            "ou",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 6.dp),
            color = Color.Black
        )
        HorizontalDivider(modifier = Modifier.weight(1f)) // Right divider
    }
}

@Preview
@Composable
private fun DividerAuthPreview() {
    RotinaAppTheme {
       // DividerAuth()
    }

}