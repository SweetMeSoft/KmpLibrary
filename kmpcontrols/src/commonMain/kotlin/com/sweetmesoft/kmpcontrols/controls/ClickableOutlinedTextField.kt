package com.sweetmesoft.kmpcontrols.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ClickableOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    color: Color,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            maxLines = 1,
            label = { Text(title) },
            readOnly = true,
            singleLine = true,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = color,
                cursorColor = color,
                focusedLabelColor = color,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(Color.Transparent)
                .clickable(enabled = enabled) {
                    onClick()
                }
        )
    }
}