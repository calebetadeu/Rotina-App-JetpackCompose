package com.example.rotinaapp.features.auth.presentation._common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.example.rotinaapp.ui.theme.RotinaAppTheme

@Composable
fun InputBasic(
    modifier: Modifier = Modifier,
    onInputFocusChanged: ((Boolean) -> Unit)? = null,
    onInputTextChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String? = null,
    leadingIcon: ImageVector? = null,
    isInputChecked: Boolean = false,
    label: String,
    inputText: String
) {
    val localFocusManager = LocalFocusManager.current
    val successColor = Color(0xFF279F70)
    val colors = TextFieldDefaults.colors(
        disabledTextColor = Color.DarkGray,
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        disabledLabelColor = Color.DarkGray,
        disabledPlaceholderColor = Color.DarkGray,
    )
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                onInputFocusChanged?.let {
                    it(focusState.isFocused)
                }
            },


        value = inputText,
        colors = colors,
        onValueChange = onInputTextChanged,
        isError = !errorMessage.isNullOrBlank(),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus(true)
        },
        label = { Text(label) },
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Leading Icon",
                    tint = successColor
                )
            } else null
        },
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