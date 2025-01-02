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
import com.sweetmesoft.kmplibrary.tools.toLocalString
import kmp_library.library.generated.resources.Accept
import kmp_library.library.generated.resources.Cancel
import kmp_library.library.generated.resources.Date
import kmp_library.library.generated.resources.Res
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.stringResource

@Composable
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
    Box(modifier = modifier) {
        OutlinedTextField(
            value = value.toLocalString(),
            onValueChange = {},
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
                        showDate = true
                    }
                }
        )
    }

    if (showDate) {
        CalendarDatePicker(
            value = value.date,
            color = color,
            minDate = minDate.date,
            maxDate = maxDate.date,
            acceptText = stringResource(Res.string.Accept),
            cancelText = stringResource(Res.string.Cancel),
            onDateSelected = { selectedDate ->
                selectedDateTime = LocalDateTime(
                    selectedDate.year,
                    selectedDate.monthNumber,
                    selectedDate.dayOfMonth,
                    value.hour,
                    value.minute
                )
                showDate = false
                showTime = true
            },
            onDismiss = {
                showDate = false
                showTime = false
                selectedDateTime = value
            }
        )
    }

    if (showTime) {
        ClockTimePicker(
            value = value.time,
            color = color,
            acceptText = stringResource(Res.string.Accept),
            cancelText = stringResource(Res.string.Cancel),
            onTimeSelected = { selectedTime ->
                selectedDateTime = LocalDateTime(
                    selectedDateTime.year,
                    selectedDateTime.monthNumber,
                    selectedDateTime.dayOfMonth,
                    selectedTime.hour,
                    selectedTime.minute
                )
                showTime = false
                showDate = false
                onSelectedDateTime(selectedDateTime)
            },
            onDismiss = {
                showTime = false
                showDate = false
                selectedDateTime = value
            }
        )
    }
}