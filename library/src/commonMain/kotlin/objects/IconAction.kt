package objects

import androidx.compose.ui.graphics.vector.ImageVector

data class IconAction(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val showIcon: Boolean = true
)