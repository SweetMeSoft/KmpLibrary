package com.sweetmesoft.kmplibrary.controls.commonList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmplibrary.tools.emptyFunction
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus

@Composable
inline fun <reified T : Any> LocalList(
    modifier: Modifier = Modifier,
    title: String = "",
    list: List<T>,
    noinline addClick: (() -> Unit) = emptyFunction,
    crossinline itemContent: (@Composable (Int, T) -> Unit)
) {
    Column(modifier = modifier) {
        if (title.isNotEmpty()) {
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = title,
                    fontSize = 24.sp,
                )

                Spacer(Modifier.weight(1f))

                if (addClick != emptyFunction) {
                    IconButton(onClick = { addClick() }) {
                        Icon(
                            imageVector = TablerIcons.Plus,
                            contentDescription = "Add list event",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Divider(
                color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (list.any()) {
            LazyColumn(modifier) {
                itemsIndexed(list) { index, item ->
                    itemContent(index, item)
                }
            }
        } else {
            EmptyList(modifier)
        }
    }
}