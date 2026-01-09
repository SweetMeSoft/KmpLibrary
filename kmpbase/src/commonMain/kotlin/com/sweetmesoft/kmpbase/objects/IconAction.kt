package com.sweetmesoft.kmpbase.objects

import androidx.compose.ui.graphics.vector.ImageVector

data class IconAction(
    val icon: ImageVector? = null,
    val text: String = "",
    val showStacked: Boolean = false,
    val showIcon: Boolean = true,
    val onClick: () -> Unit
)