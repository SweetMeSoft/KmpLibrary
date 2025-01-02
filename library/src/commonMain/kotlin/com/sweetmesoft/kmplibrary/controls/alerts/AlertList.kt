package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sweetmesoft.kmplibrary.controls.DialogFooter
import com.sweetmesoft.kmplibrary.controls.commonDropDown.SimpleDropDown

@Composable
internal fun AlertList() {
    var value by remember { mutableStateOf("") }

    if (PopupHandler.listShow) {
        Dialog(
            onDismissRequest = {
                PopupHandler.listShow = false
                PopupHandler.listDismiss()
                value = ""
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().background(
                    MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(16.dp)
                ).padding(16.dp)
            ) {
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
                    color = if (PopupHandler.listColor == Color.Unspecified) MaterialTheme.colors.primary else PopupHandler.listColor
                )

                DialogFooter(
                    modifier = Modifier.align(alignment = Alignment.End),
                    acceptText = PopupHandler.listAcceptText,
                    cancelText = PopupHandler.listCancelText,
                    color = PopupHandler.listColor,
                    onAccept = {
                        PopupHandler.listShow = false
                        PopupHandler.listAccept(value)
                        value = ""
                    }
                ) {
                    PopupHandler.listShow = false
                    PopupHandler.listDismiss()
                    value = ""
                }
            }
        }
    }
}