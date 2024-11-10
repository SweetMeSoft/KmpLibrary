package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
internal fun AlertConfirm() {
    if (PopupHandler.confirmShow) {
        Dialog(
            onDismissRequest = {
                PopupHandler.confirmShow = false
                PopupHandler.confirmDismiss()
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier.background(
                    MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(8.dp)
                ).padding(16.dp)
            ) {
                Text(
                    PopupHandler.confirmTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(PopupHandler.confirmMessage, fontSize = 14.sp)
                Row(modifier = Modifier.align(alignment = Alignment.End).padding(top = 8.dp)) {
                    TextButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            PopupHandler.confirmShow = false
                            PopupHandler.confirmDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.error,
                            backgroundColor = Color.Transparent
                        )
                    ) {
                        Text(PopupHandler.confirmCancelText)
                    }
                    TextButton(
                        onClick = {
                            PopupHandler.confirmShow = false
                            PopupHandler.confirmAccept()
                        }
                    ) {
                        Text(PopupHandler.confirmAcceptText)
                    }
                }
            }
        }
    }
}