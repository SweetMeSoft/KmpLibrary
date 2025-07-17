package com.sweetmesoft.kmplibrary.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmplibrary.controls.LoadingView
import com.sweetmesoft.kmplibrary.controls.alerts.AlertConfirm
import com.sweetmesoft.kmplibrary.controls.alerts.AlertList
import com.sweetmesoft.kmplibrary.controls.alerts.AlertProgress
import com.sweetmesoft.kmplibrary.controls.alerts.AlertPrompt
import com.sweetmesoft.kmplibrary.controls.alerts.AlertView
import com.sweetmesoft.kmplibrary.objects.IconAction
import com.sweetmesoft.kmplibrary.tools.SetNavigationBarColor
import com.sweetmesoft.kmplibrary.tools.SetStatusBarColor
import com.sweetmesoft.kmplibrary.tools.emptyFunction
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBack
import compose.icons.tablericons.Check

@Composable
fun BaseScreen(
    title: String = "",
    showTop: Boolean = false,
    edgeToEdge: Boolean = false,
    modifier: Modifier = Modifier,
    fabAction: () -> Unit = emptyFunction,
    fabIcon: ImageVector = TablerIcons.Check,
    toolbarColor: Color = MaterialTheme.colors.background,
    toolbarIconsLight: Boolean = MaterialTheme.colors.isLight,
    navigationColor: Color = MaterialTheme.colors.background,
    navigationIconsLight: Boolean = MaterialTheme.colors.isLight,
    iconActions: List<IconAction> = listOf(),
    content: @Composable (PaddingValues) -> Unit
) {
    SetStatusBarColor(toolbarColor, toolbarIconsLight)
    SetNavigationBarColor(navigationColor, navigationIconsLight)

    ScreenContent(
        modifier,
        title,
        showTop,
        edgeToEdge,
        fabAction,
        fabIcon,
        toolbarColor,
        toolbarIconsLight,
        iconActions
    ) {
        content(it)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    title: String,
    showTop: Boolean,
    edgeToEdge: Boolean,
    fabAction: () -> Unit,
    fabIcon: ImageVector,
    toolbarColor: Color,
    toolbarIconsLight: Boolean,
    iconActions: List<IconAction> = listOf(),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        contentWindowInsets = if (edgeToEdge) WindowInsets(0, 0, 0, 0) else WindowInsets.systemBars,
        modifier = modifier
            .fillMaxSize()
            .background(toolbarColor)
            .windowInsetsPadding(
                if (edgeToEdge) WindowInsets(
                    0,
                    0,
                    0,
                    0
                ) else WindowInsets.safeDrawing
            ),
        topBar = {
            if (title.isNotEmpty() || showTop) {
                TopAppBar(
                    backgroundColor = toolbarColor,
                    contentColor = if (toolbarIconsLight) Color.Black else Color.White,
                    elevation = 0.dp,
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(title)
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                if (iconActions.any()) {
                                    iconActions.forEach { action ->
                                        if (action.showIcon) {
                                            IconButton(
                                                onClick = action.onClick,
                                                modifier = Modifier.padding(start = 12.dp)
                                                    .size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = action.icon,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(4.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    navigationIcon = if (BaseViewModel.navigator.canPop) {
                        {
                            IconButton(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = { BaseViewModel.navigator.pop() }
                            ) {
                                Icon(
                                    imageVector = TablerIcons.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    } else {
                        null
                    }
                )
            }
        },
        floatingActionButton = {
            if (fabAction != emptyFunction) {
                FloatingActionButton(onClick = fabAction) {
                    Icon(
                        imageVector = fabIcon,
                        contentDescription = "fabIcon",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) {
        content(it)

        AlertView()
        AlertConfirm()
        AlertList()
        AlertPrompt()
        AlertProgress()
    }

    LoadingView()
}