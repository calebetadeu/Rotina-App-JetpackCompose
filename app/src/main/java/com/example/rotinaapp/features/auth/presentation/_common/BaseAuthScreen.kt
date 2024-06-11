package com.example.rotinaapp.features.auth.presentation._common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rotinaapp.coreUi.Background
import com.example.rotinaapp.coreUi.Primary
import com.example.rotinaapp.coreUi.Secondary
import com.example.rotinaapp.coreUi.presentOne

@Composable
fun BaseAuthScreen(
    modifier: Modifier = Modifier,
    topBarHeight: Dp,
    content: @Composable () -> Unit

) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = Primary,

        ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(it),
        ) {
            TopBarAuth(topBarHeight)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Background),
                horizontalAlignment = Alignment.CenterHorizontally



            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .background(Background),

                    horizontalAlignment = Alignment.CenterHorizontally


                ) {
                    content()

                }

            }


        }

    }
}

@Composable
fun TopBarAuth(height: Dp) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(
                height
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("ROUTINE", fontSize = 36.sp, color = Secondary, fontFamily = presentOne)

    }
}

