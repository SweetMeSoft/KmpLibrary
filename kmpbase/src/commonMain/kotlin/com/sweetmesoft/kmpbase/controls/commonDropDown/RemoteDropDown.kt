package com.sweetmesoft.kmpbase.controls.commonDropDown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.sweetmesoft.kmpbase.tools.NetworkUtils.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Any> RemoteDropDown(
    modifier: Modifier = Modifier,
    url: String = "",
    title: String,
    value: String,
    bearer: String = "",
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    crossinline selectValue: (T) -> Unit,
    crossinline itemContent: (@Composable (T) -> Unit)
) {
    var expanded by remember { mutableStateOf(false) }
    var list by remember { mutableStateOf<List<T>>(emptyList()) }
    var textFieldSize by remember { mutableStateOf(Size(0f, 0f)) }
    LaunchedEffect(url, bearer) {
        val result = get<List<T>>(url, false, bearer)
        result.onSuccess {
            list = it.obj
        }
    }

    ExposedDropdownMenuBox(
        modifier = modifier, expanded = expanded, onExpandedChange = {
            if (enabled) {
                expanded = true
            }
        }) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(
                ExposedDropdownMenuAnchorType.PrimaryEditable,
                enabled
            ).onSizeChanged {
                    textFieldSize = it.toSize()
                },
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
        val widthInDp = with(LocalDensity.current) { textFieldSize.width.toDp() }

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            Box(
                modifier = Modifier.fillMaxWidth().height(250.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.width(widthInDp).height(250.dp)
                ) {
                    items(list) { item ->
                        DropdownMenuItem(modifier = Modifier.fillMaxWidth(), onClick = {
                            selectValue(item)
                            expanded = false
                        }, text = {
                            itemContent(item)
                        })
                    }
                }
            }
        }
    }
}