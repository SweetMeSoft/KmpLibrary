package com.sweetmesoft.kmpbase.controls.commonList

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
inline fun <reified T : Any> LocalGridList(
    modifier: Modifier = Modifier,
    list: List<T>,
    columns: Int = 2,
    spaceBetween: Dp = 16.dp,
    crossinline itemContent: (@Composable (Int, T) -> Unit)
) {
    GridBase(modifier, list, columns, spaceBetween, itemContent)
}