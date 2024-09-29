package controls.alerts

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlertView(
    title: String,
    message: String,
    showDialog: Boolean,
    acceptText: String = "Aceptar",
    dismiss: () -> Unit = {}
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = { dismiss() },
            title = { Text(title) },
            text = { Text(message) },
            shape = RoundedCornerShape(8.dp),
            confirmButton = {
                Button(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    onClick = { dismiss() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(acceptText)
                }
            }
        )
    }
}