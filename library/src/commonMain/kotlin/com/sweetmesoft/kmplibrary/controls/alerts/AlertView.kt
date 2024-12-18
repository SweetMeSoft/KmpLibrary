package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                modifier = Modifier.fillMaxWidth().background(
                    MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(16.dp)
                ).padding(16.dp)
            ) {
                Text(
                    PopupHandler.alertTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(PopupHandler.alertMessage, fontSize = 14.sp)
                TextButton(
                    modifier = Modifier.padding(top = 16.dp).align(Alignment.End),
                    onClick = {
                        PopupHandler.alertShow = false
                        PopupHandler.alertDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if (PopupHandler.alertColor == Color.Unspecified) MaterialTheme.colors.primary else PopupHandler.alertColor,
                        backgroundColor = Color.Transparent
                    )
                ) {
                    Text(PopupHandler.alertAcceptText)
                }
            }
        }
    }
}