package controls.alerts

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
import controls.commonDropDown.SimpleDropDown

@Composable
fun AlertList(
    title: String,
    message: String,
    options: List<String>,
    show: Boolean,
    confirmText: String = "Aceptar",
    cancelText: String = "Cancelar",
    dismiss: () -> Unit = {},
    accept: (String) -> Unit,
) {
    var value by remember { mutableStateOf("") }

    if (show) {
        AlertDialog(
            title = { Text(title) },
            onDismissRequest = {
                PopupHandler.listShow = false
                dismiss()
            },
            text = {
                Column {
                    Text(message)
                    SimpleDropDown(
                        modifier = Modifier.fillMaxWidth(),
                        title = title,
                        list = options,
                        selectValue = { value = it },
                        value = value
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    PopupHandler.listShow = false
                    accept(value)
                }) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        PopupHandler.listShow = false
                        dismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                ) {
                    Text(cancelText)
                }
            }
        )
    }
}