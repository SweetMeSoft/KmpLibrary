package com.sweetmesoft.kmpbase.base

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Check
import dev.seyfarth.tablericons.outlined.X

@Composable
fun BaseBottomSheetScreen(
    title: String = "",
    showTop: Boolean = false,
    modifier: Modifier = Modifier,
    fabAction: () -> Unit = emptyFunction,
    fabIcon: ImageVector = TablerIcons.Outlined.Check,
    toolbarColor: Color = MaterialTheme.colorScheme.background,
    toolbarIconsLight: Boolean = !isSystemInDarkTheme(),
    navigationColor: Color = MaterialTheme.colorScheme.background,
    navigationIconsLight: Boolean = !isSystemInDarkTheme(),
    iconActions: List<IconAction> = listOf(),
    content: @Composable () -> Unit
) {
    SetStatusBarColor(toolbarColor, toolbarIconsLight)
    SetNavigationBarColor(navigationColor, navigationIconsLight)

    ScreenContent(
        modifier, title, showTop, fabAction, fabIcon, toolbarColor, toolbarIconsLight, iconActions
    ) {
        content()
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
    toolbarIconsLight: Boolean,
    iconActions: List<IconAction> = listOf(),
    content: @Composable () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        modifier = modifier.fillMaxSize().background(toolbarColor)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            if (title.isNotEmpty() || showTop) {
                TopAppBar(
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
                                            modifier = Modifier.padding(start = 12.dp).size(28.dp)
                                        ) {
                                            action.icon?.let {
                                                Icon(
                                                    imageVector = it,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(4.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }, navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = { BaseViewModel.bottomSheetNavigator.hide() }) {
                        Icon(
                            imageVector = TablerIcons.Outlined.X, contentDescription = "Close"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = toolbarColor,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
        }) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }

        AlertView()
        AlertConfirm()
        AlertList()
        AlertPrompt()
        AlertProgress()
    }

    LoadingView()
}