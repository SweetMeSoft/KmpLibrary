package com.sweetmesoft.kmptestapp.screens.pickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.controls.CalculatorPopup
import com.sweetmesoft.kmpbase.controls.ProfilePhoto
import com.sweetmesoft.kmpbase.controls.alerts.PopupHandler
import com.sweetmesoft.kmpbase.tools.getCurrentDateTime
import com.sweetmesoft.kmpcontrols.dialogs.CalendarDialog
import com.sweetmesoft.kmpcontrols.dialogs.ClockDialog
import com.sweetmesoft.kmpcontrols.pickers.DatePicker
import com.sweetmesoft.kmpcontrols.pickers.DateTimePicker
import com.sweetmesoft.kmpcontrols.pickers.TimePicker
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class PickersScreen : Screen {
    @Composable
    override fun Content() {
        remember { PickersViewModel() }
        BaseScreen(
            toolbarColor = MaterialTheme.colorScheme.primary,
            toolbarIconsLight = false,
            title = "Pickers",
            edgeToEdge = false
        ) {
            var date: LocalDate by remember { mutableStateOf(getCurrentDateTime().date) }
            var time: LocalTime by remember { mutableStateOf(getCurrentDateTime().time) }
            var dateTime: LocalDateTime by remember { mutableStateOf(getCurrentDateTime()) }
            var showDatePicker by remember { mutableStateOf(false) }
            var showTimePicker by remember { mutableStateOf(false) }
            var showCalculator by remember { mutableStateOf(false) }
            val scope = androidx.compose.runtime.rememberCoroutineScope()

            Column(
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    ProfilePhoto(
                        urlImage = "https://picsum.photos/200", radius = 120.dp, onClick = {
                            scope.launch {
                                PopupHandler.displayAlert(
                                    "Profile Photo", "You clicked the profile photo"
                                )
                            }
                        })
                }

                DatePicker(value = date) { date = it }
                TimePicker(value = time) { time = it }
                DateTimePicker(value = dateTime) { dateTime = it }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        content = { Text("Show Date") },
                        onClick = { showDatePicker = true })
                    Button(
                        modifier = Modifier.weight(1f),
                        content = { Text("Show Time") },
                        onClick = { showTimePicker = true })
                }

                Button(modifier = Modifier.fillMaxWidth(), onClick = { showCalculator = true }) {
                    Text("Show Calculator")
                }

                CalendarDialog(
                    isVisible = showDatePicker,
                    value = date,
                    onDismiss = { showDatePicker = false }) { selectedDate ->
                    date = selectedDate
                    showDatePicker = false
                }

                ClockDialog(
                    isVisible = showTimePicker,
                    value = time,
                    onDismiss = { showTimePicker = false }) { selectedTime ->
                    time = selectedTime
                    showTimePicker = false
                }

                CalculatorPopup(
                    visible = showCalculator,
                    onDismissRequest = { showCalculator = false },
                    onResult = { result ->
                        showCalculator = false
                        scope.launch {
                            PopupHandler.displayAlert(
                                "Calculator Result", "The result is $result"
                            )
                        }
                    })
            }
        }
    }
}
