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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal inline fun <reified T : Any> CommonDropDown(
    modifier: Modifier = Modifier,
    list: List<T> = listOf(),
    url: String = "",
    title: String,
    textProperty: String,
    idProperty: String,
    crossinline selectValue: (CommonDropDownItem) -> Unit
) {
    val vm: CommonDropDownViewModel =
        remember { CommonDropDownViewModel(url, list, textProperty, idProperty) }

    if (vm.state.isLoading) {
        vm.start<T>()
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = vm.state.expanded,
        onExpandedChange = {
            vm.clicked()
        }
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = vm.state.selectedOption,
            onValueChange = { vm.selectOption(it) },
            label = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = vm.state.expanded
                )
            },
            readOnly = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )
        ExposedDropdownMenu(
            expanded = vm.state.expanded,
            onDismissRequest = { vm.clicked() },
            modifier = Modifier.wrapContentWidth()
        ) {
            vm.state.list.forEach {
                DropdownMenuItem(onClick = {
                    selectValue(it)
                    vm.selectOption(it.text)
                    vm.clicked()
                }) {
                    Text(
                        text = it.text,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
