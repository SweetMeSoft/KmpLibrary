package com.sweetmesoft.kmpcontrols.controls

import androidx.compose.ui.graphics.Color

data class MarkerMap(
    val isVisible: Boolean = false,
    val title: String = "",
    val snippet: String = "",
    val iconColor: Color = Color.Red,
    val onClick: (GeoPosition) -> Boolean = { false },
    val onInfoWindowClick: (GeoPosition) -> Unit = {}
)