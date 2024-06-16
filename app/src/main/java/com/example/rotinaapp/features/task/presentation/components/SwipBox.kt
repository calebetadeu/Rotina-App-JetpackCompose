package com.example.rotinaapp.features.task.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.rotinaapp.core.domain._util.EMPTY_STRING
import com.example.rotinaapp.coreUi.Secondary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    onChangeColorDelete: @Composable () -> Unit,
    onChangeColorDefault: @Composable () -> Unit,
    enableDismissFromEndToStart:Boolean=true,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    lateinit var icon: ImageVector
    lateinit var alignment: Alignment
    val color: Color = Secondary
    val textIcon = remember {
        mutableStateOf("Excluir")
    }
    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            onChangeColorDelete()
            icon = Icons.Outlined.Delete
            alignment = Alignment.Center
            textIcon.value = "Excluir"
        }

        else -> {
            onChangeColorDefault()
            alignment = Alignment.CenterEnd
            icon = Icons.Outlined.Edit
            textIcon.value = String.EMPTY_STRING
        }
    }

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                //.background(color)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.minimumInteractiveComponentSize(),
                        imageVector = icon, contentDescription = null
                    )
                    Text(textIcon.value)

                }

            }
        },
        enableDismissFromEndToStart = enableDismissFromEndToStart,
        enableDismissFromStartToEnd = false
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            onDelete()
        }

        else -> {
        }
    }
}