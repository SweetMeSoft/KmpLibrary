package com.sweetmesoft.kmplibrary.controls.pickers

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sweetmesoft.kmplibrary.controls.ClickableOutlinedTextField
import com.sweetmesoft.kmplibrary.controls.ClockPicker
import com.sweetmesoft.kmplibrary.tools.toLocalString
import kmp_library.library.generated.resources.Accept
import kmp_library.library.generated.resources.Cancel
import kmp_library.library.generated.resources.Hour
import kmp_library.library.generated.resources.Res
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    value: LocalTime,
    color: Color = MaterialTheme.colors.primary,
    title: String = stringResource(Res.string.Hour),
    enabled: Boolean = true,
    onSelectedTime: (LocalTime) -> Unit
) {
    var showPicker: Boolean by remember { mutableStateOf(false) }
    ClickableOutlinedTextField(modifier = modifier,
        value = value.toLocalString(),
        title = title,
        color = color,
        enabled = enabled,
        onClick = {
            showPicker = true
        }
    )

    ClockPicker(
        isVisible = showPicker,
        value = value,
        color = color,
        acceptText = stringResource(Res.string.Accept),
        cancelText = stringResource(Res.string.Cancel),
        onTimeSelected = { selectedDate ->
            onSelectedTime(selectedDate)
            showPicker = false
        },
        onDismiss = { showPicker = false }
    )
}