package com.sweetmesoft.kmpbase.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.sweetmesoft.kmpbase.base.BaseBottomBarScreen.Companion.currentTab
import com.sweetmesoft.kmpbase.controls.LoadingView
import com.sweetmesoft.kmpbase.controls.alerts.AlertConfirm
import com.sweetmesoft.kmpbase.controls.alerts.AlertList
import com.sweetmesoft.kmpbase.controls.alerts.AlertProgress
import com.sweetmesoft.kmpbase.controls.alerts.AlertPrompt
import com.sweetmesoft.kmpbase.controls.alerts.AlertView
import com.sweetmesoft.kmpbase.theme.disabledColor
import com.sweetmesoft.kmpbase.theme.disabledColorText
import com.sweetmesoft.kmpbase.tools.SetNavigationBarColor
import com.sweetmesoft.kmpbase.tools.SetStatusBarColor

class BaseBottomBarScreen {
    companion object {
        var currentTab: MutableState<Int> = mutableStateOf(0)
    }
}

@Composable
fun BaseBottomBarScreen(
    modifier: Modifier = Modifier,
    navigationBarItemColors: NavigationBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicatorColor = MaterialTheme.colorScheme.primary,
        disabledIconColor = disabledColor,
        disabledTextColor = disabledColorText
    ),
    navigationBarColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    tabs: List<BaseTab>
) {
    SetNavigationBarColor(navigationBarColor, true)
    TabNavigator(tabs.first(), tabDisposable = {
        TabDisposable(
            it,
            tabs,
        )
    }) {
        ScreenContent(
            modifier, navigationBarItemColors, navigationBarColor, tabs
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    modifier: Modifier,
    navigationBarItemColors: NavigationBarItemColors,
    navigationBarColor: Color,
    tabs: List<BaseTab>
) {
    val tab = tabs[currentTab.value]

    SetStatusBarColor(tab.baseOptions.toolbarColor, tab.baseOptions.statusDarkIcons)
    SetNavigationBarColor(tab.baseOptions.navigationColor, tab.baseOptions.navigationDarkIcons)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (tab.baseOptions.showTop || (tab.baseOptions.title.isNotEmpty() && tab.baseOptions.showTop)) {
                CustomToolbar(
                    tab.baseOptions.title,
                    tab.baseOptions.iconActions,
                    tab.baseOptions.toolbarColor,
                    tab.baseOptions.onToolbarColor
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = navigationBarColor
            ) {
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
                        colors = navigationBarItemColors
                    )
                }
            }
        },
    ) {
        Box(modifier = Modifier.padding(it).fillMaxSize()) {
            CurrentTab()
        }

        AlertView()
        AlertConfirm()
        AlertList()
        AlertPrompt()
        AlertProgress()
    }

    LoadingView()
}