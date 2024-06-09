package com.example.rotinaapp.features.auth.presentation._common.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rotinaapp.R
import com.example.rotinaapp.coreUi.RotinaAppTheme

@Composable

fun ButtonSocial(
    modifier: Modifier = Modifier,
    textButton: String,
    isLoading: Boolean,
    onClick: () -> Unit,

) {

    Button(
        onClick = onClick,

        colors = ButtonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.clickable {
                    onClick()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = modifier
                        .border(
                            shape = RoundedCornerShape(60.dp),
                            color = Color.LightGray,
                            width = 0.2.dp
                        )
                        .size(30.dp)
                )
            }
            Spacer(Modifier.width(2.dp))
            if(isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = Color.White,
                )
            }else{
                Text(textButton)
            }


        }
    }
}

@Preview
@Composable
private fun ButtonSocialPreview() {
    RotinaAppTheme {
        ButtonSocial(
            modifier = Modifier,
            onClick = {},
            textButton = "Google",
            isLoading = false

        )
    }


}