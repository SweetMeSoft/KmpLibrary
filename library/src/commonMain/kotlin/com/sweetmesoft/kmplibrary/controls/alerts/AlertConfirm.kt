package com.sweetmesoft.kmplibrary.controls.alerts

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
import kmp_library.library.generated.resources.Accept
import kmp_library.library.generated.resources.Cancel
import kmp_library.library.generated.resources.Res
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AlertConfirm(
    title: String,
    message: String,
    confirmText: String = stringResource(Res.string.Accept),
    cancelText: String =  stringResource(Res.string.Cancel),
    dismiss: () -> Unit = {},
    accept: () -> Unit,
) {
    if (PopupHandler.confirmShow) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = {
                PopupHandler.confirmShow = false
                dismiss()
            },
            title = { Text(title) },
            text = { Text(message) },
            shape = RoundedCornerShape(8.dp),
            confirmButton = {
                Button(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    onClick = {
                        PopupHandler.confirmShow = false
                        accept()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    onClick = {
                        PopupHandler.confirmShow = false
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