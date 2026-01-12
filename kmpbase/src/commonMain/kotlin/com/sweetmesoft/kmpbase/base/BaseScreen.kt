package com.sweetmesoft.kmpbase.base

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmpbase.controls.LoadingView
import com.sweetmesoft.kmpbase.controls.alerts.AlertConfirm
import com.sweetmesoft.kmpbase.controls.alerts.AlertList
import com.sweetmesoft.kmpbase.controls.alerts.AlertProgress
import com.sweetmesoft.kmpbase.controls.alerts.AlertPrompt
import com.sweetmesoft.kmpbase.controls.alerts.AlertView
import com.sweetmesoft.kmpbase.objects.IconAction
import com.sweetmesoft.kmpbase.tools.SetNavigationBarColor
import com.sweetmesoft.kmpbase.tools.SetStatusBarColor
import com.sweetmesoft.kmpbase.tools.emptyFunction
import compose.icons.TablerIcons
import compose.icons.tablericons.Check

@Composable
fun BaseScreen(
    title: String = "",
    showTop: Boolean = false,
    modifier: Modifier = Modifier,
    fabAction: () -> Unit = emptyFunction,
    fabIcon: ImageVector = TablerIcons.Check,
    toolbarColor: Color = MaterialTheme.colorScheme.surface,
    onToolbarColor: Color = MaterialTheme.colorScheme.onSurface,
    statusDarkIcons: Boolean = !isSystemInDarkTheme(),
    navigationDarkIcons: Boolean = !isSystemInDarkTheme(),
    navigationColor: Color = Color.Transparent,
    iconActions: List<IconAction> = listOf(),
    content: @Composable (PaddingValues) -> Unit
) {
    ScreenContent(
        modifier,
        title,
        showTop,
        fabAction,
        fabIcon,
        toolbarColor,
        onToolbarColor,
        statusDarkIcons,
        navigationDarkIcons,
        navigationColor,
        iconActions
    ) {
        content(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    modifier: Modifier,
    title: String,
    showTop: Boolean,
    fabAction: () -> Unit,
    fabIcon: ImageVector,
    toolbarColor: Color,
    onToolbarColor: Color,
    statusDarkIcons: Boolean,
    navigationDarkIcons: Boolean,
    navigationColor: Color,
    iconActions: List<IconAction> = listOf(),
    content: @Composable (PaddingValues) -> Unit
) {
    SetStatusBarColor(toolbarColor, statusDarkIcons)
    SetNavigationBarColor(navigationColor, navigationDarkIcons)
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier.fillMaxSize().background(toolbarColor),
        topBar = {
            if (title.isNotEmpty() || showTop) {
                CustomToolbar(title, iconActions, toolbarColor, onToolbarColor)
            }
        },
        floatingActionButton = {
            if (fabAction != emptyFunction) {
                Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                    FloatingActionButton(onClick = fabAction) {
                        Icon(
                            imageVector = fabIcon,
                            contentDescription = "fabIcon",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            content(it)
        }

        AlertView()
        AlertConfirm()
        AlertList()
        AlertPrompt()
        AlertProgress()
    }

    LoadingView()
}