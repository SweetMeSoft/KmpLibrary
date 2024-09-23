package controls.commonDropDown


import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal inline fun <reified T : Any> LocalDropDown(
    modifier: Modifier = Modifier,
    list: List<T> = listOf(),
    title: String,
    textProperty: String,
    crossinline selectValue: (T) -> Unit,
    crossinline itemContent: (@Composable (T) -> Unit)
) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = { text = it },
            readOnly = true,
            singleLine = true,
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
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            list.forEach {
                DropdownMenuItem(onClick = {
                    selectValue(it)
                    val jsonElement = Json.encodeToJsonElement(it)
                    text = jsonElement.jsonObject[textProperty]?.let { jsonValue ->
                        when {
                            jsonValue is JsonPrimitive && jsonValue.isString -> jsonValue.content
                            else -> jsonValue
                        }
                    }.toString()
                    expanded = false
                }) {
                    itemContent(it)
                }
            }
        }
    }
}