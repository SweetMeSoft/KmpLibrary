package com.sweetmesoft.kmplibrary.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
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

class BaseBottomBarScreen {
    companion object {
        var currentTab: MutableState<Int> = mutableStateOf(0)
    }
}

@Composable
fun BaseBottomBarScreen(
    modifier: Modifier = Modifier,
    tabs: List<BaseTab>
) {
    TabNavigator(tabs.first(), tabDisposable = {
        TabDisposable(
            it,
            tabs,
        )
    }) {
        ScreenContent(
            modifier,
            tabs
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    tabs: List<BaseTab>
) {
    val tab = tabs[currentTab.value]
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            if (tab.baseOptions.title.isNotEmpty() || tab.baseOptions.showTop) {
                TopAppBar(
                    backgroundColor = tab.baseOptions.toolbarColor,
                    contentColor = if (tab.baseOptions.toolbarIconsLight) Color.Black else Color.White,
                    elevation = 0.dp,
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(tab.baseOptions.title)
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
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
                            }
                        }
                    },
                    navigationIcon = {
                        if (BaseViewModel.navigator.canPop) {
                            Icon(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clickable { BaseViewModel.navigator.pop() },
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            BottomNavigation {
                val tabNavigator = LocalTabNavigator.current
                tabs.forEach {
                    BottomNavigationItem(selected = tabNavigator.current.key == it.key,
                        selectedContentColor = Color.White,
                        unselectedContentColor = MaterialTheme.colors.primaryVariant,
                        label = { Text(it.options.title) },
                        icon = {
                            Icon(
                                it.options.icon!!,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        onClick = {
                            tabNavigator.current = it
                            currentTab.value = tabs.indexOf(it)
                        })
                }
            }
        },
    ) {
        CurrentTab()

        AlertView()
        AlertConfirm()
        AlertList()
        AlertPrompt()
        AlertProgress()
    }

    LoadingView()
}