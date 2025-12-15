package com.sweetmesoft.kmpbase.controls.commonDropDown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> LargeDropdownMenu(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    items: List<T>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    selectedItemToString: (T) -> String = { it.toString() },
    itemContent: (@Composable (T) -> Unit),
    color: Color = MaterialTheme.colorScheme.primary,
    drawItem: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit = { item, selected, itemEnabled, onClick ->
        LargeDropdownMenuItem(
            text = item.toString(),
            selected = selected,
            enabled = itemEnabled,
            onClick = onClick,
        )
    },
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            label = {
            Text(
                label, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        },
            value = items.getOrNull(selectedIndex)?.let { selectedItemToString(it) } ?: "",
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = enabled
                )
            },
            onValueChange = { },
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = color,
                cursorColor = color,
                focusedLabelColor = color,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Surface(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp)
                .clickable(enabled = enabled) { expanded = true },
            color = Color.Transparent,
        ) {}
    }
    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
            ) {
                val listState = rememberLazyListState()
                if (selectedIndex > -1) {
                    LaunchedEffect("ScrollToSelected") {
                        listState.scrollToItem(index = selectedIndex)
                    }
                }

                Column {
                    Text(
                        label, modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(), state = listState
                    ) {
                        itemsIndexed(items) { index, item ->
                            Box(modifier = Modifier.clickable {
                                onItemSelected(index, item)
                                expanded = false
                            }) {
                                itemContent(items[index])
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LargeDropdownMenuItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.clickable(enabled) { onClick() }.fillMaxWidth().padding(16.dp)) {
        Text(
            text = text,
        )
    }
}