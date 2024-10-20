package com.sweetmesoft.kmplibrary.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import tools.SetNavigationBarColor
import tools.SetStatusBarColor

@Composable
fun BaseBottomBarScreen(
    title: String = "",
    showTop: Boolean = false,
    modifier: Modifier = Modifier,
    toolbarColor: Color = MaterialTheme.colors.background,
    navigationColor: Color = MaterialTheme.colors.background,
    tabs: List<Tab>
) {
    SetStatusBarColor(toolbarColor, MaterialTheme.colors.isLight)
    SetNavigationBarColor(navigationColor, MaterialTheme.colors.isLight)

    TabNavigator(tabs.first(), tabDisposable = {
        TabDisposable(
            it,
            tabs,
        )
    }) {
        ScreenContent(
            { CurrentTab() },
            modifier,
            title,
            showTop,
            toolbarColor,
            tabs
        )
    }
}

@Composable
private fun ScreenContent(
    content: @Composable () -> Unit,
    modifier: Modifier,
    title: String,
    showTop: Boolean,
    toolbarColor: Color,
    tabs: List<Tab>
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            if (title.isNotEmpty() || showTop) {
                TopAppBar(
                    backgroundColor = toolbarColor,
                    elevation = 0.dp,
                    title = {
                        Text(title)
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
                        onClick = { tabNavigator.current = it })
                }
            }
        },
    ) {
        content()
    }
}