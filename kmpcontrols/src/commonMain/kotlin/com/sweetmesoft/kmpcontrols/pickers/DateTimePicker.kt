package com.sweetmesoft.kmpcontrols.pickers

import androidx.annotation.RequiresFeature
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sweetmesoft.kmpcontrols.controls.ClickableOutlinedTextField
import com.sweetmesoft.kmpcontrols.dialogs.CalendarDialog
import com.sweetmesoft.kmpcontrols.dialogs.ClockDialog
import com.sweetmesoft.kmpcontrols.utils.toLocalString
import kmplibrary.kmpcontrols.generated.resources.Accept
import kmplibrary.kmpcontrols.generated.resources.Cancel
import kmplibrary.kmpcontrols.generated.resources.Date
import kmplibrary.kmpcontrols.generated.resources.Res
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number
import org.jetbrains.compose.resources.stringResource

@Composable
@RequiresFeature(name = "VIBRATE Android Permission", enforcement = "")
fun DateTimePicker(
    modifier: Modifier = Modifier,
    value: LocalDateTime,
    color: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    title: String = stringResource(Res.string.Date),
    minDate: LocalDateTime = LocalDateTime(1900, 1, 1, 0, 0),
    maxDate: LocalDateTime = LocalDateTime(2100, 12, 31, 23, 59),
    onSelectedDateTime: (LocalDateTime) -> Unit
) {
    var showDate: Boolean by remember { mutableStateOf(false) }
    var showTime: Boolean by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf(value) }
    ClickableOutlinedTextField(
        modifier = modifier,
        value = value.toLocalString(),
        title = title,
        color = color,
        enabled = enabled,
        onClick = {
            showDate = true
        }
    )

    CalendarDialog(
        isVisible = showDate,
        value = value.date,
        color = color,
        minDate = minDate.date,
        maxDate = maxDate.date,
        acceptText = stringResource(Res.string.Accept),
        cancelText = stringResource(Res.string.Cancel),
        onDismiss = {
            showDate = false
            showTime = false
            selectedDateTime = value
        }
    ) { selectedDate ->
        selectedDateTime = LocalDateTime(
            selectedDate.year,
            selectedDate.month.number,
            selectedDate.day,
            value.hour,
            value.minute
        )
        showDate = false
        showTime = true
    }

    @Suppress("RequiresFeature")
    ClockDialog(
        isVisible = showTime,
        value = value.time,
        color = color,
        acceptText = stringResource(Res.string.Accept),
        cancelText = stringResource(Res.string.Cancel),
        onDismiss = {
            showTime = false
            showDate = false
            selectedDateTime = value
        }
    ) { selectedTime ->
        selectedDateTime = LocalDateTime(
            selectedDateTime.year,
            selectedDateTime.month.number,
            selectedDateTime.day,
            selectedTime.hour,
            selectedTime.minute
        )
        showTime = false
        showDate = false
        onSelectedDateTime(selectedDateTime)
    }
}