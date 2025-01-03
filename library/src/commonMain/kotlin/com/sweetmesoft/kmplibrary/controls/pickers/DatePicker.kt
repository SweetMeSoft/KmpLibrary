package com.sweetmesoft.kmplibrary.controls.pickers

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sweetmesoft.kmplibrary.controls.CalendarPicker
import com.sweetmesoft.kmplibrary.controls.ClickableOutlinedTextField
import com.sweetmesoft.kmplibrary.tools.toLocalString
import kmp_library.library.generated.resources.Accept
import kmp_library.library.generated.resources.Cancel
import kmp_library.library.generated.resources.Date
import kmp_library.library.generated.resources.Res
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    value: LocalDate,
    color: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    title: String = stringResource(Res.string.Date),
    minDate: LocalDate = LocalDate(1900, 1, 1),
    maxDate: LocalDate = LocalDate(2100, 12, 31),
    onSelectedDate: (LocalDate) -> Unit
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

    CalendarPicker(
        isVisible = showPicker,
        value = value,
        color = color,
        minDate = minDate,
        maxDate = maxDate,
        acceptText = stringResource(Res.string.Accept),
        cancelText = stringResource(Res.string.Cancel),
        onDateSelected = { selectedDate ->
            onSelectedDate(selectedDate)
            showPicker = false
        },
        onDismiss = { showPicker = false }
    )
}