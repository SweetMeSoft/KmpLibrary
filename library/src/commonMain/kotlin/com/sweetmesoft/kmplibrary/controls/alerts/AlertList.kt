package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import com.sweetmesoft.kmplibrary.controls.commonDropDown.SimpleDropDown

@Composable
internal fun AlertList() {
    var value by remember { mutableStateOf("") }

    if (PopupHandler.listShow) {
        Dialog(
            onDismissRequest = {
                PopupHandler.listShow = false
                PopupHandler.listDismiss()
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
                    color = if (PopupHandler.confirmColor == Color.Unspecified) MaterialTheme.colors.primary else PopupHandler.confirmColor
                )
                Row(modifier = Modifier.align(alignment = Alignment.End).padding(top = 8.dp)) {
                    TextButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            PopupHandler.listShow = false
                            PopupHandler.listDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.error,
                            backgroundColor = Color.Transparent
                        )
                    ) {
                        Text(PopupHandler.listCancelText)
                    }
                    TextButton(
                        onClick = {
                            PopupHandler.listShow = false
                            PopupHandler.listAccept(value)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = if (PopupHandler.confirmColor == Color.Unspecified) MaterialTheme.colors.primary else PopupHandler.confirmColor,
                            backgroundColor = Color.Transparent
                        )
                    ) {
                        Text(PopupHandler.listAcceptText)
                    }
                }
            }
        }
    }
}