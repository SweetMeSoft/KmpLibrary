package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kmp_library.library.generated.resources.Res
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AlertView(
    title: String,
    message: String,
    dismiss: () -> Unit = {}
) {
    if (PopupHandler.alertShow) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = {
                PopupHandler.alertShow = false
                dismiss()
            },
            title = { Text(title) },
            text = { Text(message) },
            shape = RoundedCornerShape(8.dp),
            confirmButton = {
                TextButton(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    onClick = {
                        PopupHandler.alertShow = false
                        dismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(PopupHandler.alertAcceptText)
                }
            }
        )
    }
}