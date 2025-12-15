package com.sweetmesoft.kmpbase.controls.commonDropDown

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Any> LocalDropDown(
    modifier: Modifier = Modifier,
    list: List<T> = listOf(),
    title: String,
    value: String,
    color: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true,
    crossinline selectValue: (T) -> Unit,
    crossinline itemContent: (@Composable (T) -> Unit)
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier, expanded = expanded, onExpandedChange = {
            if (enabled) {
                expanded = !expanded
            }
        }) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, enabled),
            value = value,
            onValueChange = { },
            readOnly = true,
            singleLine = true,
            enabled = enabled,
            label = {
                Text(
                    text = title, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = color,
                cursorColor = color,
                focusedLabelColor = color,
                unfocusedContainerColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentWidth()
        ) {
            list.forEach {
                DropdownMenuItem(onClick = {
                    selectValue(it)
                    expanded = false
                }, text = {
                    itemContent(it)
                })
            }
        }
    }
}