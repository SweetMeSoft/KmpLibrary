package com.sweetmesoft.kmptestapp.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpcontrols.dialogs.CalendarDialog
import com.sweetmesoft.kmpcontrols.dialogs.ClockDialog
import com.sweetmesoft.kmpcontrols.pickers.DatePicker
import com.sweetmesoft.kmpcontrols.pickers.DateTimePicker
import com.sweetmesoft.kmpcontrols.pickers.TimePicker
import com.sweetmesoft.kmpcontrols.utils.getCurrentDateTime
import com.sweetmesoft.kmpcontrols.utils.toLocalString
import com.sweetmesoft.kmplibrary.base.BaseScreen
import com.sweetmesoft.kmplibrary.base.BaseViewModel.Companion.navigator
import com.sweetmesoft.kmplibrary.controls.alerts.PopupHandler
import com.sweetmesoft.kmplibrary.objects.IconAction
import com.sweetmesoft.kmpmaps.MapComponent
import com.sweetmesoft.kmpmaps.controls.Coordinates
import com.sweetmesoft.kmpmaps.controls.RouteMap
import com.sweetmesoft.kmptestapp.screens.about.AboutScreen
import compose.icons.TablerIcons
import compose.icons.tablericons.DotsVertical
import kotlinx.coroutines.launch
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
                }
            )
        ) {
            val scope = rememberCoroutineScope()
            var date: LocalDate by remember { mutableStateOf(getCurrentDateTime().date) }
            var time: LocalTime by remember { mutableStateOf(getCurrentDateTime().time) }
            var dateTime: LocalDateTime by remember { mutableStateOf(getCurrentDateTime()) }
            var showDatePicker by remember { mutableStateOf(false) }
            var showTimePicker by remember { mutableStateOf(false) }
            var promptInput by remember { mutableStateOf("") }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DatePicker(
                    value = date
                ) {
                    date = it
                }
                TimePicker(
                    value = time
                ) {
                    time = it
                }
                DateTimePicker(
                    value = dateTime
                ) {
                    dateTime = it
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        content = {
                            Text("Show Date")
                        },
                        onClick = {
                            showDatePicker = true
                        }
                    )
                    Button(
                        modifier = Modifier.weight(1f),
                        content = {
                            Text("Show Time")
                        },
                        onClick = {
                            showTimePicker = true
                        }
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        content = {
                            Text("Alert")
                        },
                        onClick = {
                            scope.launch {
                                PopupHandler.displayAlert(
                                    "The is a alert dialog",
                                    "Here you can show a message. For example, the hour is " + time.toLocalString()
                                )
                            }
                        }
                    )

                    Button(
                        modifier = Modifier.weight(1f),
                        content = {
                            Text("Confirm")
                        },
                        onClick = {
                            scope.launch {
                                PopupHandler.displayConfirm(
                                    "This is a confirm dialog",
                                    "Here you can show a message to user can select yes or no, accept or cancel, and do something accordingly",
                                    dismiss = {
                                        scope.launch {
                                            PopupHandler.displayAlert(
                                                "You selected",
                                                "No"
                                            )
                                        }
                                    }
                                ) {
                                    scope.launch {
                                        PopupHandler.displayAlert(
                                            "You selected",
                                            "Yes"
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        content = {
                            Text("Prompt")
                        },
                        onClick = {
                            scope.launch {
                                PopupHandler.displayPrompt(
                                    "This is a prompt dialog",
                                    "Here you can show a message to user to enter some text",
                                    input = promptInput
                                ) {
                                    promptInput = it
                                    scope.launch {
                                        PopupHandler.displayAlert(
                                            "Your prompt was",
                                            it
                                        )
                                    }
                                }
                            }
                        }
                    )
                    Button(
                        modifier = Modifier.weight(1f),
                        content = {
                            Text("List")
                        },
                        onClick = {
                            scope.launch {
                                PopupHandler.displayList(
                                    "This is a list dialog",
                                    "Here you can show to the user a list of options, and the user can select one of them",
                                    options = listOf("Option 1", "Option 2", "Option 3")
                                ) {
                                    scope.launch {
                                        PopupHandler.displayAlert(
                                            "Option selected",
                                            it
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
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
                    isVisible = showDatePicker,
                    value = date,
                    onDismiss = { showDatePicker = false }
                ) { selectedDate ->
                    date = selectedDate
                    showDatePicker = false
                }

                ClockDialog(
                    isVisible = showTimePicker,
                    value = time,
                    onDismiss = { showTimePicker = false }
                ) { selectedTime ->
                    time = selectedTime
                    showTimePicker = false
                }

                MapComponent(
                    coordinates = Coordinates(4.7, -74.12),
                    zoom = 15f,
                    routes = listOf(
                        RouteMap(
                            points = List(10) {
                                Coordinates(
                                    latitude = 4.7 + ((0..10).random() - 0.5) * 0.1,
                                    longitude = -74.12 + ((0..10).random() - 0.5) * 0.1
                                )
                            },
                            color = Color.Red,
                            width = 5f
                        ),
                        RouteMap(
                            points = List(10) {
                                Coordinates(
                                    latitude = 4.8 + ((0..10).random() - 0.5) * 0.1,
                                    longitude = -74.5 + ((0..10).random() - 0.5) * 0.1
                                )
                            },
                            color = Color.Green,
                            width = 5f
                        )
                    )
                )
            }
        }
    }
}