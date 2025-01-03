package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmpcontrols.dialogs.BaseDialog

@Composable
internal fun AlertView() {
    if (PopupHandler.alertShow) {
        BaseDialog(
            acceptText = PopupHandler.alertAcceptText,
            cancelText = "",
            color = PopupHandler.alertColor,
            onAccept = {
                PopupHandler.alertShow = false
                PopupHandler.alertDismiss()
            },
            onDismiss = { }
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    PopupHandler.alertTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(PopupHandler.alertMessage, fontSize = 14.sp)
            }
        }
    }
}