package com.sweetmesoft.kmpcontrols.pickers

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sweetmesoft.kmpbase.tools.toLocalString
import com.sweetmesoft.kmpcontrols.controls.ClickableOutlinedTextField
import com.sweetmesoft.kmpcontrols.dialogs.CalendarDialog
import kmplibrary.kmpcontrols.generated.resources.Accept
import kmplibrary.kmpcontrols.generated.resources.Cancel
import kmplibrary.kmpcontrols.generated.resources.Date
import kmplibrary.kmpcontrols.generated.resources.Res
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    value: LocalDate,
    color: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true,
    title: String = stringResource(Res.string.Date),
    minDate: LocalDate = LocalDate(1900, 1, 1),
    maxDate: LocalDate = LocalDate(2100, 12, 31),
    onSelectedDate: (LocalDate) -> Unit
) {
    var showPicker: Boolean by remember { mutableStateOf(false) }
    ClickableOutlinedTextField(
        modifier = modifier,
        value = value.toLocalString(),
        title = title,
        color = color,
        enabled = enabled,
        onClick = {
            showPicker = true
        })

    CalendarDialog(
        isVisible = showPicker,
        value = value,
        color = color,
        minDate = minDate,
        maxDate = maxDate,
        acceptText = stringResource(Res.string.Accept),
        cancelText = stringResource(Res.string.Cancel),
        onDismiss = { showPicker = false }) { selectedDate ->
        onSelectedDate(selectedDate)
        showPicker = false
    }
}