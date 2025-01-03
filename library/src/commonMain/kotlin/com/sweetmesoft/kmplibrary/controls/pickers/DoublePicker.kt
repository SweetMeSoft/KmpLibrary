package com.sweetmesoft.kmplibrary.controls.pickers

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sweetmesoft.kmplibrary.controls.CalculatorPopup
import com.sweetmesoft.kmplibrary.controls.ClickableOutlinedTextField

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
    ClickableOutlinedTextField(modifier = modifier,
        value = value,
        title = title,
        color = color,
        enabled = enabled,
        onClick = {
            showCalculator = true
        }
    )

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