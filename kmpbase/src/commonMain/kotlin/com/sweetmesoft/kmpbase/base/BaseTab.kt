package com.sweetmesoft.kmpbase.base

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.sweetmesoft.kmpbase.objects.IconAction
import com.sweetmesoft.kmpbase.tools.emptyFunction
import compose.icons.TablerIcons
import compose.icons.tablericons.Check

data class BaseTabOptions(
    val title: String,
    val icon: Painter? = null,
    val showTop: Boolean = false,
    val modifier: Modifier = Modifier,
    var fabAction: () -> Unit = emptyFunction,
    val fabIcon: ImageVector = TablerIcons.Check,
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
    fabIcon: ImageVector = TablerIcons.Check,
    toolbarColor: Color = MaterialTheme.colorScheme.background,
    toolbarIconsLight: Boolean = !isSystemInDarkTheme(),
    navigationColor: Color = MaterialTheme.colorScheme.background,
    navigationIconsLight: Boolean = !isSystemInDarkTheme(),
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
                index = 0u, title = baseOptions.title, icon = baseOptions.icon
            )
        }
}