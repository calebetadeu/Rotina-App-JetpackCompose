package com.example.rotinaapp.features.task.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rotinaapp.coreUi.BackgroundTask
import com.example.rotinaapp.coreUi.Title

@Composable
fun TaskCard(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // val selectedTask = remember { mutableStateOf(false) }
    val color = remember { mutableStateOf(BackgroundTask) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            // .padding(vertical = 8.dp)
            .size(60.dp),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(
            containerColor = color.value,
            contentColor = Title,
            disabledContainerColor = BackgroundTask,
            disabledContentColor = Color.LightGray,
        ),
        onClick = {

        },
        enabled = !isSelected


    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            SwipeBox(
                onEdit = {

                },
                onDelete = {},
                onChangeColorDefault = {
                    color.value = BackgroundTask
                },
                onChangeColorDelete = {
                    color.value = MaterialTheme.colorScheme.errorContainer
                },
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = onClick
                    )
                    Spacer(Modifier.height(12.dp))

                    Column {
                        Text("Café da manhã", fontSize = 14.sp)

                        Text("Descrição", fontSize = 12.sp, textAlign = TextAlign.Start)
                    }


                }
            }


        }


    }
}
