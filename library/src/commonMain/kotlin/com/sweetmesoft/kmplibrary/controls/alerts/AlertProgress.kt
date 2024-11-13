package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
internal fun AlertProgress() {
    if (PopupHandler.progressShow) {
        Dialog(
            onDismissRequest = {
                PopupHandler.progressShow = false
                PopupHandler.progressDismiss()
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().background(
                    MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(8.dp)
                ).padding(16.dp)
            ) {
                Text(
                    PopupHandler.progressTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = PopupHandler.progressProgress
                )
                Text(
                    text = (PopupHandler.progressProgress * 100).toInt().toString() + "/100",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    textAlign = TextAlign.End,
                    fontSize = 12.sp
                )
                TextButton(
                    modifier = Modifier.align(alignment = Alignment.End),
                    onClick = {
                        PopupHandler.progressShow = false
                        PopupHandler.progressDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colors.error,
                        backgroundColor = Color.Transparent
                    )
                ) {
                    Text(PopupHandler.progressCancelText)
                }
            }
        }
    }
}