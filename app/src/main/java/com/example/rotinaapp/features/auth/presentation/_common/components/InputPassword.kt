package com.example.rotinaapp.features.auth.presentation._common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.rotinaapp.R

@Composable
fun InputPassword(
    passwordText: String,
    onPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = false,
    label: String,
    onVisibilityIconClick: () -> Unit = {},
    errorMessage: String? = null,
    onPasswordFocusChanged: ((Boolean) -> Unit)? = null,
) {
    val localFocusManager = LocalFocusManager.current

    val shape = Shapes().medium
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                onPasswordFocusChanged?.let { it(focusState.isFocused) }
            }
            .size(65.dp
            )
        ,
       // colors = colors,
        value = passwordText,
        onValueChange = { onPasswordChanged(it) },
        isError = !errorMessage.isNullOrBlank(),
        supportingText = {
            if (!errorMessage.isNullOrBlank()) {
                Text(
                    color = MaterialTheme.colorScheme.error,
                    text = errorMessage
                )
            }
        },
        label = {
            Text(
                text = label
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus(true)
        },
        shape = shape,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onVisibilityIconClick
            ) {
                Icon(
                    painter = if (isVisible) painterResource(id = R.drawable.visibility) else painterResource(
                        id = R.drawable.visibility_off
                    ),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        }
    )
}
