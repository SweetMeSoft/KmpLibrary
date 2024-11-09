package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
internal fun AlertView() {
    if (PopupHandler.alertShow) {
        Dialog(
            onDismissRequest = {
                PopupHandler.alertShow = false
                PopupHandler.alertDismiss()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Column(
                modifier = Modifier.background(
                    MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(16.dp)
                ).padding(16.dp)
            ) {
                Text(PopupHandler.alertTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(PopupHandler.alertMessage, fontSize = 14.sp)
                TextButton(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    onClick = {
                        PopupHandler.alertShow = false
                        PopupHandler.alertDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(PopupHandler.alertAcceptText)
                }
            }
        }
    }
}