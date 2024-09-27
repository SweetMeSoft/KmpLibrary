package controls.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
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
    onConfirm: (String) -> Unit,
    show: Boolean,
    hide: () -> Unit = {},
) {
    var value by remember { mutableStateOf("") }
    var showAlertList by remember { mutableStateOf(show) }

    if (showAlertList) {
        AlertDialog(
            title = { Text(title) },
            onDismissRequest = { hide() },
            text = {
                Column {
                    Text(message)
                    SimpleDropDown(
                        modifier = Modifier.fillMaxWidth(),
                        title = "Usuarios",
                        list = options,
                        selectValue = { value = it },
                        value = value
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(value)
                    showAlertList = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    hide()
                    showAlertList = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
