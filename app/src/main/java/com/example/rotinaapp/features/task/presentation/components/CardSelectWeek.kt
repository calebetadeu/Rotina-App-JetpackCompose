package com.example.rotinaapp.features.task.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rotinaapp.coreUi.Background
import com.example.rotinaapp.coreUi.Secondary
import com.example.rotinaapp.coreUi.Title


@Composable
fun CardSelectWeek(
    days: Int,
    isSelected: Boolean,
    onClick: () -> Unit

) {

    val backgroundColor = if (isSelected) {
        Secondary
    } else {
        Background
    }
    Card(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = Title,
            disabledContainerColor = Background,
            disabledContentColor = Title,

            ),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick

    ) {
        this.AnimatedVisibility(true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    days.toString(),
                    fontSize = 16.sp
                    // modifier = Modifier.fillMaxSize(),
                    //textAlign = TextAlign.Center
                )
            }

        }
    }
}
