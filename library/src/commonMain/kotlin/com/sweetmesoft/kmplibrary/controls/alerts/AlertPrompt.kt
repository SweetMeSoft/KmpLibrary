package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmpcontrols.dialogs.BaseDialog

@Composable
internal fun AlertPrompt() {
    var text by remember { mutableStateOf(PopupHandler.promptInput) }
    if (PopupHandler.promptShow) {
        BaseDialog(
            acceptText = PopupHandler.promptAcceptText,
            cancelText = PopupHandler.promptCancelText,
            color = PopupHandler.promptColor,
            onAccept = {
                PopupHandler.promptShow = false
                PopupHandler.promptAccept(text)
                text = ""
            },
            onDismiss = {
                text = ""
                PopupHandler.promptShow = false
                PopupHandler.promptDismiss()
            }
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    PopupHandler.promptTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    PopupHandler.promptSubtitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    value = text,
                    label = { Text(PopupHandler.promptPlaceholder) },
                    onValueChange = { text = it },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = if (PopupHandler.promptColor == Color.Unspecified) MaterialTheme.colorScheme.primary else PopupHandler.promptColor,
                        cursorColor = if (PopupHandler.promptColor == Color.Unspecified) MaterialTheme.colorScheme.primary else PopupHandler.promptColor,
                        focusedLabelColor = if (PopupHandler.promptColor == Color.Unspecified) MaterialTheme.colorScheme.primary else PopupHandler.promptColor,
                    )
                )
            }
        }
    }
}