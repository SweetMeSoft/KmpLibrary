package com.sweetmesoft.kmptestapp.screens.dialogs

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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmplibrary.base.BaseScreen
import com.sweetmesoft.kmplibrary.controls.alerts.PopupHandler
import com.sweetmesoft.kmplibrary.tools.getCurrentDateTime
import com.sweetmesoft.kmplibrary.tools.toLocalString
import kotlinx.coroutines.launch

class DialogsScreen : Screen {
    @Composable
    override fun Content() {
        remember { DialogsViewModel() }
        BaseScreen(
            toolbarColor = MaterialTheme.colorScheme.primary,
            toolbarIconsLight = false,
            title = "Dialogs",
            edgeToEdge = false
        ) {
            val scope = rememberCoroutineScope()
            var promptInput by remember { mutableStateOf("") }
            val time = getCurrentDateTime().time

            Column(
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(modifier = Modifier.weight(1f), content = {
                        Text("Alert")
                    }, onClick = {
                        scope.launch {
                            PopupHandler.displayAlert(
                                "The is a alert dialog",
                                "Here you can show a message. For example, the hour is " + time.toLocalString()
                            )
                        }
                    })

                    Button(modifier = Modifier.weight(1f), content = {
                        Text("Confirm")
                    }, onClick = {
                        scope.launch {
                            PopupHandler.displayConfirm(
                                "This is a confirm dialog",
                                "Here you can show a message to user can select yes or no, accept or cancel, and do something accordingly",
                                dismiss = {
                                    scope.launch {
                                        PopupHandler.displayAlert(
                                            "You selected", "No"
                                        )
                                    }
                                }) {
                                scope.launch {
                                    PopupHandler.displayAlert(
                                        "You selected", "Yes"
                                    )
                                }
                            }
                        }
                    })
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(modifier = Modifier.weight(1f), content = {
                        Text("Prompt")
                    }, onClick = {
                        scope.launch {
                            PopupHandler.displayPrompt(
                                "This is a prompt dialog",
                                "Here you can show a message to user to enter some text",
                                input = promptInput
                            ) {
                                promptInput = it
                                scope.launch {
                                    PopupHandler.displayAlert(
                                        "Your prompt was", it
                                    )
                                }
                            }
                        }
                    })
                    Button(modifier = Modifier.weight(1f), content = {
                        Text("List")
                    }, onClick = {
                        scope.launch {
                            PopupHandler.displayList(
                                "This is a list dialog",
                                "Here you can show to the user a list of options, and the user can select one of them",
                                options = listOf("Option 1", "Option 2", "Option 3")
                            ) {
                                scope.launch {
                                    PopupHandler.displayAlert(
                                        "Option selected", it
                                    )
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}
