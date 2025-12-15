package com.sweetmesoft.kmptestapp.screens.main

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpcontrols.dialogs.CalendarDialog
import com.sweetmesoft.kmpcontrols.dialogs.ClockDialog
import com.sweetmesoft.kmpcontrols.pickers.DatePicker
import com.sweetmesoft.kmpcontrols.pickers.DateTimePicker
import com.sweetmesoft.kmpcontrols.pickers.TimePicker
import com.sweetmesoft.kmpcontrols.utils.getCurrentDateTime
import com.sweetmesoft.kmplibrary.base.BaseScreen
import com.sweetmesoft.kmplibrary.base.BaseViewModel.Companion.navigator
import com.sweetmesoft.kmplibrary.objects.IconAction
import com.sweetmesoft.kmptestapp.screens.about.AboutScreen
import com.sweetmesoft.kmptestapp.screens.dialogs.DialogsScreen
import com.sweetmesoft.kmptestapp.screens.map.MapScreen
import compose.icons.TablerIcons
import compose.icons.tablericons.DotsVertical
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class MainScreen : Screen {
    @Suppress("RequiresFeature")
    @Composable
    override fun Content() {
        remember { MainViewModel() }
        BaseScreen(
            toolbarColor = MaterialTheme.colorScheme.primary,
            toolbarIconsLight = false,
            title = "KMP TestApp",
            edgeToEdge = false,
            iconActions = listOf(
                IconAction(TablerIcons.DotsVertical, true) {
                    navigator.push(AboutScreen())
                })
        ) {
            rememberCoroutineScope()
            var date: LocalDate by remember { mutableStateOf(getCurrentDateTime().date) }
            var time: LocalTime by remember { mutableStateOf(getCurrentDateTime().time) }
            var dateTime: LocalDateTime by remember { mutableStateOf(getCurrentDateTime()) }
            var showDatePicker by remember { mutableStateOf(false) }
            var showTimePicker by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Open Dialogs") },
                    onClick = { navigator.push(DialogsScreen()) })

                //                rememberImageCropper()
                //                var openImagePicker by remember { mutableStateOf(value = false) }
                //                ProfilePhoto(
                //                    urlImage = vm.state.urlImage,
                //                ) {
                //                    openImagePicker = true
                //                }
                //
                //                CMPImagePickNCropDialog(
                //                    imageCropper = imageCropper,
                //                    openImagePicker = openImagePicker,
                //                    imagePickerDialogHandler = {
                //                        openImagePicker = it
                //                    },
                //                    selectedImageCallback = {
                //                        vm.updatePhoto(it)
                //                        selectedImage = it
                //                    },
                //                    selectedImageFileCallback = {
                //                    }
                //                )
                CalendarDialog(
                    isVisible = showDatePicker, value = date, onDismiss = { showDatePicker = false }) { selectedDate ->
                    date = selectedDate
                    showDatePicker = false
                }

                ClockDialog(
                    isVisible = showTimePicker, value = time, onDismiss = { showTimePicker = false }) { selectedTime ->
                    time = selectedTime
                    showTimePicker = false
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Open Map") },
                    onClick = { navigator.push(MapScreen()) })
            }
        }
    }
}
