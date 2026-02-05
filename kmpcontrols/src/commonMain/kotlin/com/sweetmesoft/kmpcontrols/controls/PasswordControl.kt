package com.sweetmesoft.kmpcontrols.controls

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Eye
import dev.seyfarth.tablericons.outlined.EyeOff
import dev.seyfarth.tablericons.outlined.Lock
import kmplibrary.kmpcontrols.generated.resources.Password
import kmplibrary.kmpcontrols.generated.resources.Res
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordControl(
    modifier: Modifier = Modifier,
    value: String,
    label: String = stringResource(Res.string.Password),
    colorLight: Color = MaterialTheme.colorScheme.primary,
    colorDark: Color = MaterialTheme.colorScheme.secondary,
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            if (it.length <= 30) {
                text = it
                onValueChange(it)
            }
        },
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.None,
            keyboardType = KeyboardType.Password,
            capitalization = KeyboardCapitalization.None
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = TablerIcons.Outlined.Lock, contentDescription = "Password"
            )
        },
        trailingIcon = {
            val image = if (isPasswordVisible) TablerIcons.Outlined.Eye
            else TablerIcons.Outlined.EyeOff

            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {
                Icon(
                    image,
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier.height(20.dp),
                )
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (!isSystemInDarkTheme()) colorLight else colorDark,
            cursorColor = if (!isSystemInDarkTheme()) colorLight else colorDark,
            focusedLabelColor = if (!isSystemInDarkTheme()) colorLight else colorDark,
        )
    )
}