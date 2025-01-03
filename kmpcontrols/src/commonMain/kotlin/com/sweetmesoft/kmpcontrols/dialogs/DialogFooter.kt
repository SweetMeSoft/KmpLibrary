package com.sweetmesoft.kmpcontrols.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DialogFooter(
    modifier: Modifier = Modifier,
    acceptText: String,
    cancelText: String,
    color: Color,
    onAccept: () -> Unit,
    onDismiss: () -> Unit
) {
    Row(
        modifier = modifier.padding(bottom = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        if (cancelText.isNotEmpty()) {
            TextButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.error,
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(cancelText)
            }
        }

        if (acceptText.isNotEmpty()) {
            TextButton(
                onClick = {
                    onAccept()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = if (color == Color.Unspecified) MaterialTheme.colors.primary else color,
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(acceptText)
            }
        }
    }
}