package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun AlertProgress() {
    if (PopupHandler.progressShow) {
        BaseDialog(
            acceptText = "",
            cancelText = PopupHandler.progressCancelText,
            color = PopupHandler.progressColor,
            onAccept = { },
            onDismiss = {
                PopupHandler.progressShow = false
                PopupHandler.progressDismiss()
            }) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    PopupHandler.progressTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { PopupHandler.progressProgress },
                    color = if (PopupHandler.progressColor == Color.Unspecified) MaterialTheme.colorScheme.primary else PopupHandler.progressColor
                )
                Text(
                    text = (PopupHandler.progressProgress * 100).toInt().toString() + "/100",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    textAlign = TextAlign.End,
                    fontSize = 12.sp
                )
            }
        }
    }
}