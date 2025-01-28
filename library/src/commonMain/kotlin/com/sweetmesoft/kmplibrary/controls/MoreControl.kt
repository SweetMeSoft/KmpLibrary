package com.sweetmesoft.kmplibrary.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MoreControl(modifier: Modifier = Modifier, options: List<ItemOption>) {
    var showMenu by remember { mutableStateOf(false) }

    Box(modifier) {
        IconButton(
            onClick = { showMenu = true },
            modifier = modifier.size(20.dp)
        ) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "Options Icon"
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            options.forEach { item ->
                DropdownMenuItem(onClick = {
                    item.onClick()
                    showMenu = false
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (item.icon != null) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "Option",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(item.text)
                    }
                }
            }
        }
    }
}

data class ItemOption(
    val text: String,
    val icon: ImageVector? = null,
    val onClick: () -> Unit
)