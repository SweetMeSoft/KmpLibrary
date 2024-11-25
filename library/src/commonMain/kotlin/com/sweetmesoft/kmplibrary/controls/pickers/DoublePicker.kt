package com.sweetmesoft.kmplibrary.controls.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmplibrary.controls.CalculatorPopup

@Composable
fun DoublePicker(
    modifier: Modifier = Modifier,
    title: String,
    color: Color = MaterialTheme.colors.primary,
    value: String = "",
    enabled: Boolean = true,
    onValueChange: (Double) -> Unit
) {
    var showCalculator: Boolean by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            maxLines = 1,
            label = { Text(title) },
            readOnly = true,
            singleLine = true,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                cursorColor = color,
                focusedLabelColor = color,
            )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(Color.Transparent)
                .clickable {
                    if (enabled) {
                        showCalculator = true
                    }
                }
        )
    }

    CalculatorPopup(
        visible = showCalculator,
        onDismissRequest = {
            showCalculator = false
        }
    ) {
        onValueChange(it)
        showCalculator = false
    }
}