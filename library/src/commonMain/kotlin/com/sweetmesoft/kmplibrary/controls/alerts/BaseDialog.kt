package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun BaseDialog(
    acceptText: String,
    cancelText: String,
    color: Color,
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(
                MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp)
            )
        ) {
            content()

            DialogFooter(
                modifier = Modifier.align(alignment = Alignment.End),
                acceptText = acceptText,
                cancelText = cancelText,
                color = color,
                onAccept = {
                    onAccept()
                }) {
                onDismiss()
            }
        }
    }
}