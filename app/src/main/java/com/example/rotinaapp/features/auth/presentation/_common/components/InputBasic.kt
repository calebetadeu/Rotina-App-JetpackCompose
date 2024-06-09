package com.example.rotinaapp.features.auth.presentation._common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.example.rotinaapp.coreUi.RotinaAppTheme
import com.example.rotinaapp.coreUi.Success

@Composable
fun InputBasic(
    modifier: Modifier = Modifier,
    onInputFocusChanged: ((Boolean) -> Unit)? = null,
    onInputTextChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String? = null,
    isInputChecked: Boolean = false,
    label: String,
    inputText: String
) {
    val localFocusManager = LocalFocusManager.current
    val successColor = Success

    val shape = Shapes().medium
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                onInputFocusChanged?.let {
                    it(focusState.isFocused)
                }
            }
          //  .size(80.dp)
        ,


        value = inputText,
        shape = shape,
        onValueChange = onInputTextChanged,
        isError = !errorMessage.isNullOrBlank(),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus(true)
        },
        label = { Text(label) },
        supportingText = {
            if (!errorMessage.isNullOrBlank()) {
                Text(
                    text = errorMessage,
                    color = Color.Red
                )

            }
        },
        trailingIcon = {
            if (errorMessage.isNullOrBlank() && isInputChecked)
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    tint = successColor,
                    contentDescription = ""
                )
        }
    )

}

@Preview
@Composable
private fun InputBasicPreview() {
    RotinaAppTheme {
        Scaffold {
            Column(
                modifier = Modifier.padding(it)
            ) {
                InputBasic(
                    inputText = "",
                    onInputTextChanged = {},
                    errorMessage = "Error",
                    label = "Email",


                    )
                InputBasic(
                    inputText = "",
                    onInputTextChanged = {},

                    label = "Email"

                )
            }
        }

    }

}