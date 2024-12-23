package com.sweetmesoft.kmpmaps.objects

import androidx.compose.ui.graphics.Color

data class MarkerMap(
    val isVisible: Boolean = false,
    val title: String = "",
    val snippet: String = "",
    val iconColor: Color = Color.Red,
    val onClick: (MarkerMap) -> Boolean = { false },
    val onInfoWindowClick: () -> Unit = {}
)