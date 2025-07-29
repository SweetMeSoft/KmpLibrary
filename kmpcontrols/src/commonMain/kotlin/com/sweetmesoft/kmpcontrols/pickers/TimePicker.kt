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
import com.sweetmesoft.kmpcontrols.dialogs.ClockDialog
import com.sweetmesoft.kmpcontrols.utils.toLocalString
import kmplibrary.kmpcontrols.generated.resources.Accept
import kmplibrary.kmpcontrols.generated.resources.Cancel
import kmplibrary.kmpcontrols.generated.resources.Hour
import kmplibrary.kmpcontrols.generated.resources.Res
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

@RequiresFeature(name = "VIBRATE Android Permission", enforcement = "")
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
    ClickableOutlinedTextField(
        modifier = modifier,
        value = value.toLocalString(),
        title = title,
        color = color,
        enabled = enabled,
        onClick = {
            showPicker = true
        }
    )

    @Suppress("RequiresFeature")
    ClockDialog(
        isVisible = showPicker,
        value = value,
        color = color,
        acceptText = stringResource(Res.string.Accept),
        cancelText = stringResource(Res.string.Cancel),
        onDismiss = { showPicker = false }
    ) { selectedDate ->
        onSelectedTime(selectedDate)
        showPicker = false
    }
}