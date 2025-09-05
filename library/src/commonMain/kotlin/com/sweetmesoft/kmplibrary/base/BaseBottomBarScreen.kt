package com.sweetmesoft.kmplibrary.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.sweetmesoft.kmplibrary.base.BaseBottomBarScreen.Companion.currentTab
import com.sweetmesoft.kmplibrary.controls.LoadingView
import com.sweetmesoft.kmplibrary.controls.alerts.AlertConfirm
import com.sweetmesoft.kmplibrary.controls.alerts.AlertList
import com.sweetmesoft.kmplibrary.controls.alerts.AlertProgress
import com.sweetmesoft.kmplibrary.controls.alerts.AlertPrompt
import com.sweetmesoft.kmplibrary.controls.alerts.AlertView
import com.sweetmesoft.kmplibrary.tools.SetStatusBarColor
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBack

class BaseBottomBarScreen {
    companion object {
        var currentTab: MutableState<Int> = mutableStateOf(0)
    }
}

@Composable
fun BaseBottomBarScreen(
    modifier: Modifier = Modifier, tabs: List<BaseTab>
) {
    TabNavigator(tabs.first(), tabDisposable = {
        TabDisposable(
            it,
            tabs,
        )
    }) {
        ScreenContent(
            modifier, tabs
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    modifier: Modifier, tabs: List<BaseTab>
) {
    val tab = tabs[currentTab.value]
    SetStatusBarColor(
        color = tab.baseOptions.toolbarColor,
        darkIcons = tab.baseOptions.toolbarIconsLight
    )
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        modifier = modifier
            .fillMaxSize()
            .background(tab.baseOptions.toolbarColor)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            if (tab.baseOptions.showTop || (tab.baseOptions.title.isNotEmpty() && tab.baseOptions.showTop)) {
                TopAppBar(
                    title = {
                        Text(tab.baseOptions.title)
                    },
                    navigationIcon = {
                        if (BaseViewModel.navigator.canPop) {
                            IconButton(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = { BaseViewModel.navigator.pop() }) {
                                Icon(
                                    imageVector = TablerIcons.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        } else {
                            null
                        }
                    },
                    actions = {
                        if (tab.baseOptions.iconActions.any()) {
                            tab.baseOptions.iconActions.forEach { action ->
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
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = tab.baseOptions.toolbarColor,
                        titleContentColor = if (tab.baseOptions.toolbarIconsLight) Color.Black else Color.White,
                        navigationIconContentColor = if (tab.baseOptions.toolbarIconsLight) Color.Black else Color.White,
                        actionIconContentColor = if (tab.baseOptions.toolbarIconsLight) Color.Black else Color.White
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar {
                val tabNavigator = LocalTabNavigator.current
                tabs.forEachIndexed { index, it ->
                    NavigationBarItem(
                        selected = currentTab.value == index,
                        label = { Text(it.options.title) },
                        icon = {
                            Icon(
                                it.options.icon!!,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        onClick = {
                            currentTab.value = index
                            tabNavigator.current = it
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (tab.baseOptions.toolbarIconsLight) Color.Black else Color.White,
                            selectedTextColor = if (tab.baseOptions.toolbarIconsLight) Color.Black else Color.White,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        },
    ) {
        Column {
            Box(modifier = Modifier.weight(1f)) {
                CurrentTab()
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        AlertView()
        AlertConfirm()
        AlertList()
        AlertPrompt()
        AlertProgress()
    }

    LoadingView()
}