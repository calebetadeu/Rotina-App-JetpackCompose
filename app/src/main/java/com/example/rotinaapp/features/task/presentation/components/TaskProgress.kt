package com.example.rotinaapp.features.task.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.rotinaapp.coreUi.Secondary

@Composable
fun TaskProgress(
    modifier: Modifier,
    maxProgress: Int,
    currentProgress: Int
) {
    val progress by remember {
        mutableFloatStateOf(currentProgress / maxProgress.toFloat())
    }
    val animateProgress by animateFloatAsState(
        progress,
        animationSpec = tween(500, 500, FastOutSlowInEasing),
        label = "animation-progress"
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { animateProgress },
            strokeCap = StrokeCap.Round,
            trackColor = Color.LightGray.copy(0.2f),
            color = Secondary
        )
        Text("$currentProgress/$maxProgress", fontSize = 14.sp, textAlign = TextAlign.Center)
    }

}