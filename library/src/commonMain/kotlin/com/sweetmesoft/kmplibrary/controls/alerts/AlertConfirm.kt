package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun AlertConfirm() {
    if (PopupHandler.confirmShow) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = {
                PopupHandler.confirmShow = false
                PopupHandler.confirmDismiss()
            },
            title = { Text(PopupHandler.confirmTitle) },
            text = { Text(PopupHandler.confirmMessage) },
            shape = RoundedCornerShape(8.dp),
            confirmButton = {
                Button(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    onClick = {
                        PopupHandler.confirmShow = false
                        PopupHandler.confirmAccept()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(PopupHandler.confirmAcceptText)
                }
            },
            dismissButton = {
                TextButton(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    onClick = {
                        PopupHandler.confirmShow = false
                        PopupHandler.confirmDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.error)
                ) {
                    Text(PopupHandler.confirmCancelText)
                }
            }
        )
    }
}