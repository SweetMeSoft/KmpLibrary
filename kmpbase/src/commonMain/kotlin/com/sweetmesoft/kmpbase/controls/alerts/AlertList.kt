package com.sweetmesoft.kmpbase.controls.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmpbase.controls.commonDropDown.SimpleDropDown

@Composable
internal fun AlertList() {
    var value by remember { mutableStateOf("") }

    if (PopupHandler.listShow) {
        BaseDialog(
            acceptText = PopupHandler.listAcceptText,
            cancelText = PopupHandler.listCancelText,
            color = PopupHandler.listColor,
            onAccept = {
                PopupHandler.listShow = false
                PopupHandler.listAccept(value)
                value = ""
            },
            onDismiss = {
                PopupHandler.listShow = false
                PopupHandler.listDismiss()
                value = ""
            }) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    PopupHandler.listTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    PopupHandler.listSubtitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                SimpleDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    title = PopupHandler.listPlaceholder,
                    list = PopupHandler.listOptions,
                    selectValue = { value = it },
                    value = value,
                    color = if (PopupHandler.listColor == Color.Unspecified) MaterialTheme.colorScheme.primary else PopupHandler.listColor
                )
            }
        }
    }
}