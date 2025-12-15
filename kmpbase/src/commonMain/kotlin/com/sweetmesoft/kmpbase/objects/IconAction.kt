package com.sweetmesoft.kmpbase.objects

import androidx.compose.ui.graphics.vector.ImageVector

data class IconAction(
    val icon: ImageVector, val showIcon: Boolean = true, val onClick: () -> Unit
)