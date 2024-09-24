package controls.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.dateTimeToString

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    value: LocalDateTime,
    selectDateTimePicker: (LocalDateTime) -> Unit
) {
    var showPicker: Boolean by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = dateTimeToString(value, "yyyy-MM-dd HH:mm"),
            onValueChange = {},
            maxLines = 1,
            label = { Text("Fecha") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            readOnly = true
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(Color.Transparent)
                .clickable {
                    showPicker = true
                }
        )
    }

    if(showPicker) {
        WheelDateTimePickerView(
            modifier = Modifier.padding(top = 18.dp, bottom = 10.dp).fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            showDatePicker = showPicker,
            containerColor = MaterialTheme.colors.surface,
            dateTextColor = MaterialTheme.colors.onSurface,
            titleStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            ),
            doneLabelStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colors.secondary,
            ),
            rowCount = 5,
            dateTextStyle = TextStyle(
                fontWeight = FontWeight(600),
                color = MaterialTheme.colors.onSurface
            ),
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            onDoneClick = {
                selectDateTimePicker(it)
                showPicker = false
            },
            onDismiss = {
                showPicker = false
            },
            height = 170.dp
        )
    }
}