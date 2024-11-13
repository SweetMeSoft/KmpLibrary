package com.sweetmesoft.kmplibrary.controls.commonList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
inline fun <reified T : Any> LocalGridList(
    modifier: Modifier = Modifier,
    list: List<T>,
    columns: Int = 2,
    crossinline itemContent: (@Composable (Int, T) -> Unit)
) {

    if (list.any()) {
        val chunkedList = list.chunked(columns)
        LazyColumn(
            modifier = modifier
        ) {
            items(chunkedList.size) { index ->
                val rowItems = chunkedList[index]
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                ) {
                    for (item in rowItems) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            itemContent(index, item)
                        }
                    }
                    if (rowItems.size < columns) {
                        for (i in rowItems.size until columns) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    } else {
        EmptyList(modifier)
    }
}