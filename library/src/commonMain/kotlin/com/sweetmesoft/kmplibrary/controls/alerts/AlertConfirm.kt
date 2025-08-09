package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmpcontrols.dialogs.BaseDialog

@Composable
internal fun AlertConfirm() {
    if (PopupHandler.confirmShow) {
        BaseDialog(
            acceptText = PopupHandler.confirmAcceptText,
            cancelText = PopupHandler.confirmCancelText,
            color = PopupHandler.confirmColor,
            onAccept = {
                PopupHandler.confirmShow = false
                PopupHandler.confirmAccept()
            },
            onDismiss = {
                PopupHandler.confirmShow = false
                PopupHandler.confirmDismiss()
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    PopupHandler.confirmTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(PopupHandler.confirmMessage, fontSize = 14.sp)
            }
        }
    }
}