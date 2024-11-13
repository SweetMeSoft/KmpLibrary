package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
internal fun AlertPrompt() {
    var text by remember { mutableStateOf(PopupHandler.promptInput) }
    if (PopupHandler.promptShow) {
        Dialog(
            onDismissRequest = {
                PopupHandler.promptShow = false
                PopupHandler.promptDismiss()
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
                    PopupHandler.promptTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    value = text,
                    label = { Text(PopupHandler.promptSubtitle) },
                    onValueChange = { text = it },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                TextButton(
                    modifier = Modifier.align(alignment = Alignment.End),
                    onClick = {
                        PopupHandler.promptShow = false
                        PopupHandler.promptAccept(text)
                        text = ""
                    }
                ) {
                    Text(PopupHandler.promptAcceptText)
                }
            }
        }
    }
}