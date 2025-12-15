package com.sweetmesoft.kmpbase.controls.commonList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
inline fun <reified T : Any> GridBase(
    modifier: Modifier = Modifier,
    list: List<T>,
    columns: Int = 2,
    spaceBetween: Dp = 16.dp,
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
                    modifier = Modifier.height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(spaceBetween)
                ) {
                    rowItems.forEachIndexed { index2, item ->
                        Box(
                            modifier = Modifier.weight(1f), contentAlignment = Alignment.Center
                        ) {
                            itemContent((index * 2) + index2, item)
                        }
                    }

                    if (rowItems.size < columns) {
                        for (i in rowItems.size until columns) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(spaceBetween))
            }
        }
    } else {
        EmptyList(modifier)
    }
}