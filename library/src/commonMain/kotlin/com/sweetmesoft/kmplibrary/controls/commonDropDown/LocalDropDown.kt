package com.sweetmesoft.kmplibrary.controls.commonDropDown

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
inline fun <reified T : Any> LocalDropDown(
    modifier: Modifier = Modifier,
    list: List<T> = listOf(),
    title: String,
    textProperty: String,
    indexSelected: Int = -1,
    color: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    crossinline selectValue: (T) -> Unit,
    crossinline itemContent: (@Composable (T) -> Unit)
) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    text = getText(indexSelected, textProperty, list)
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            if (enabled) {
                expanded = !expanded
            }
        }
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = { text = it },
            readOnly = true,
            singleLine = true,
            enabled = enabled,
            label = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                cursorColor = color,
                focusedLabelColor = color,
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            list.forEachIndexed { index, it ->
                DropdownMenuItem(onClick = {
                    selectValue(it)
                    text = getText(index, textProperty, list)
                    expanded = false
                }) {
                    itemContent(it)
                }
            }
        }
    }
}

inline fun <reified T> getText(indexSelected: Int, textProperty: String, list: List<T>): String {
    if (indexSelected == -1 || list.isEmpty()) {
        return ""
    }
    val it = list[indexSelected]
    val jsonElement = Json.encodeToJsonElement(it)
    return jsonElement.jsonObject[textProperty]?.let { jsonValue ->
        when {
            jsonValue is JsonPrimitive && jsonValue.isString -> jsonValue.content
            else -> jsonValue
        }
    }.toString()
}