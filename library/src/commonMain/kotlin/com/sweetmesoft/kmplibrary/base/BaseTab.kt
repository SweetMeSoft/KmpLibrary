package com.sweetmesoft.kmplibrary.base

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sweetmesoft.kmplibrary.objects.IconAction
import com.sweetmesoft.kmplibrary.tools.emptyFunction

data class BaseTabOptions(
    val title: String,
    val icon: Painter? = null,
    val showTop: Boolean = false,
    val modifier: Modifier = Modifier,
    var fabAction: () -> Unit = emptyFunction,
    val fabIcon: ImageVector = Icons.Default.Check,
    val toolbarColor: Color,
    val toolbarIconsLight: Boolean,
    val navigationColor: Color,
    val navigationIconsLight: Boolean,
    val iconActions: List<IconAction> = listOf()
)

@Composable
fun defaultBaseTabOptions(
    title: String,
    icon: Painter? = null,
    showTop: Boolean = false,
    modifier: Modifier = Modifier,
    fabAction: () -> Unit = emptyFunction,
    fabIcon: ImageVector = Icons.Default.Check,
    toolbarColor: Color = MaterialTheme.colors.background,
    toolbarIconsLight: Boolean = MaterialTheme.colors.isLight,
    navigationColor: Color = MaterialTheme.colors.background,
    navigationIconsLight: Boolean = MaterialTheme.colors.isLight,
    iconActions: List<IconAction> = listOf()
): BaseTabOptions {
    return BaseTabOptions(
        title = title,
        icon = icon,
        showTop = showTop,
        modifier = modifier,
        fabAction = fabAction,
        fabIcon = fabIcon,
        toolbarColor = toolbarColor,
        toolbarIconsLight = toolbarIconsLight,
        navigationColor = navigationColor,
        navigationIconsLight = navigationIconsLight,
        iconActions = iconActions
    )
}

interface BaseTab : Tab {
    val baseOptions: BaseTabOptions
        @Composable get
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 0u,
                title = baseOptions.title,
                icon = baseOptions.icon
            )
        }
}