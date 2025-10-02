package com.sweetmesoft.kmpmaps.controls

import androidx.compose.ui.graphics.Color

data class RouteMap(
    val points: List<Coordinates> = emptyList(),
    val color: Color = Color.Transparent,
    val width: Float = 1f
)