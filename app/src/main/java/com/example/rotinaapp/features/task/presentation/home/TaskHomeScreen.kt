package com.example.rotinaapp.features.task.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rotinaapp.coreUi.Background
import com.example.rotinaapp.coreUi.RotinaAppTheme
import com.example.rotinaapp.coreUi.Secondary
import com.example.rotinaapp.coreUi.TitleSubtitle
import com.example.rotinaapp.coreUi.TitleSubtitle2
import com.example.rotinaapp.features.auth.domain.model.UserModel
import com.example.rotinaapp.features.task.presentation.components.CardSelectWeek
import com.example.rotinaapp.features.task.presentation.components.TaskCard
import com.example.rotinaapp.features.task.presentation.components.TaskProgress
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAction: (TaskHomeAction) -> Unit,
    user: UserModel?
) {
    val daysMonth = getDaysMonth()
    val daysOfWeek = getDaysWeek()



    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                ),
                title = {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Olá, ${user?.name.toString()}", textAlign = TextAlign.Center)
                    }

                },
                navigationIcon = {
                    IconButton(onClick = { onAction(TaskHomeAction.OnLogout) }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                },

                actions = {
                    AsyncImage(
                        model = user?.photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )

                },
                modifier = modifier.fillMaxWidth()
            )
        },


        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 30.dp),
        ) {
            CalendarSelectDay(
                daysOfMonth = daysMonth,
                daysOfWeek = daysOfWeek
            )
            TasksList()

//            CadasterEmptyTask(
//                onCadasterTask = {
//
//                }
//            )

        }

    }
}

@Composable
fun TasksList() {
    val taskCount = 5
    val selectedTasks =
        remember { mutableStateListOf<Boolean>().apply { repeat(taskCount) { add(false) } } }

    Column(
        Modifier
            .padding(vertical = 12.dp)
            .fillMaxHeight() // Fill the available height
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Concluídas Hoje", textAlign = TextAlign.Center)
            Spacer(Modifier.width(8.dp))

            TaskProgress(
                maxProgress = taskCount,
                currentProgress = selectedTasks.count { it },
                modifier = Modifier.size(40.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f), // Take all available space
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskCount) { index ->
                TaskCard(
                    isSelected = selectedTasks[index],
                    onClick = { selectedTasks[index] = !selectedTasks[index] }
                )
            }
        }
    }
}


@Composable
fun CadasterEmptyTask(onCadasterTask: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 52.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Clique no botão abaixo para cadastrar uma tarefa",
            color = TitleSubtitle2,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
        IconButton(
            onClick = onCadasterTask,
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(CircleShape)
                .background(Secondary),
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
fun CalendarSelectDay(
    daysOfMonth: ArrayList<String>,
    daysOfWeek: ArrayList<Int>,
) {
    val selectedDay = remember { mutableIntStateOf(-1) }

    Column(
        //Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow {

            items(daysOfMonth.size) { days ->
                Text(
                    daysOfMonth[days].dateFormatText(),
                    fontSize = 18.sp,
                    color = TitleSubtitle,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
        Spacer(Modifier.height(28.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            items(daysOfWeek.size) { days ->
                CardSelectWeek(
                    days = daysOfWeek[days],
                    isSelected = selectedDay.intValue == days,
                    onClick = { selectedDay.intValue = days }
                )
            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
fun getDaysMonth(): ArrayList<String> {
    val today = LocalDate.now(ZoneId.systemDefault())
    val diasDoMes = ArrayList<String>()

    for (i in 0..6) {
        val dia = today.plusDays(i.toLong())
        val nomeDia = dia.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        diasDoMes.add(nomeDia)
    }
    return diasDoMes

}

@RequiresApi(Build.VERSION_CODES.O)
fun getDaysWeek(): ArrayList<Int> {
    val today = LocalDate.now(ZoneId.systemDefault())
    // Cria uma lista para armazenar os nomes dos dias da semana
    val daysOfWeek = ArrayList<Int>()

    // Adiciona os próximos 7 dias à lista
    for (i in 0..6) {
        val dia = today.plusDays(i.toLong()) // Adiciona 'i' dias à data atual
        val nomeDia = dia.dayOfMonth
        daysOfWeek.add(nomeDia)
    }

    return daysOfWeek

}

fun String.dateFormatText(): String {
    return this.take(3)
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomePreviewScreen() {
    RotinaAppTheme {
//        HomeScreen(
//
//        )
    }


}

