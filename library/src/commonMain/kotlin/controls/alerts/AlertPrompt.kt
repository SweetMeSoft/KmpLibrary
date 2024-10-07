package controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AlertPrompt(
    title: String,
    subtitle: String,
    input: String,
    confirmText: String = "Aceptar",
    dismiss: () -> Unit = {},
    accept: (String) -> Unit
) {
    var text by remember { mutableStateOf(input) }
    if (PopupHandler.promptShow) {
        Dialog(
            onDismissRequest = {
                PopupHandler.promptShow = false
                dismiss()
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
                Text(title, fontSize = 18.sp)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    value = text,
                    label = { Text(subtitle) },
                    onValueChange = { text = it },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                Button(
                    modifier = Modifier.align(alignment = Alignment.End),
                    onClick = {
                        PopupHandler.promptShow = false
                        accept(text)
                        text = ""
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(confirmText)
                }
            }
        }
    }
}
