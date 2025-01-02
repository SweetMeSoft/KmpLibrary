package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sweetmesoft.kmplibrary.controls.DialogFooter

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
                modifier = Modifier.fillMaxWidth().background(
                    MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(16.dp)
                ).padding(16.dp)
            ) {
                Text(
                    PopupHandler.confirmTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(PopupHandler.confirmMessage, fontSize = 14.sp)

                DialogFooter(
                    modifier = Modifier.align(alignment = Alignment.End),
                    acceptText = PopupHandler.confirmAcceptText,
                    cancelText = PopupHandler.confirmCancelText,
                    color = PopupHandler.confirmColor,
                    onAccept = {
                        PopupHandler.confirmShow = false
                        PopupHandler.confirmAccept()
                    }
                ) {
                    PopupHandler.confirmShow = false
                    PopupHandler.confirmDismiss()
                }
            }
        }
    }
}