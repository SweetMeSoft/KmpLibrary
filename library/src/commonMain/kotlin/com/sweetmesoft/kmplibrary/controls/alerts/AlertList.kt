package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sweetmesoft.kmplibrary.controls.commonDropDown.SimpleDropDown

@Composable
internal fun AlertList() {
    var value by remember { mutableStateOf("") }

    if (PopupHandler.listShow) {
        AlertDialog(
            title = { Text(PopupHandler.listTitle) },
            onDismissRequest = {
                PopupHandler.listShow = false
                PopupHandler.listDismiss()
            },
            text = {
                Column {
                    Text(PopupHandler.listMessage)
                    SimpleDropDown(
                        modifier = Modifier.fillMaxWidth(),
                        title = PopupHandler.listTitle,
                        list = PopupHandler.listOptions,
                        selectValue = { value = it },
                        value = value
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    PopupHandler.listShow = false
                    PopupHandler.listAccept(value)
                }) {
                    Text(PopupHandler.listAcceptText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        PopupHandler.listShow = false
                        PopupHandler.listDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                ) {
                    Text(PopupHandler.listCancelText)
                }
            }
        )
    }
}