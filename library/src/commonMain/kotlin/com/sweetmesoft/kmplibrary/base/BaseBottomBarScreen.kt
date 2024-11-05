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
import com.sweetmesoft.kmplibrary.controls.LoadingView
import com.sweetmesoft.kmplibrary.controls.alerts.AlertConfirm
import com.sweetmesoft.kmplibrary.controls.alerts.AlertList
import com.sweetmesoft.kmplibrary.controls.alerts.AlertProgress
import com.sweetmesoft.kmplibrary.controls.alerts.AlertPrompt
import com.sweetmesoft.kmplibrary.controls.alerts.AlertView
import com.sweetmesoft.kmplibrary.controls.alerts.PopupHandler
import com.sweetmesoft.kmplibrary.tools.SetNavigationBarColor
import com.sweetmesoft.kmplibrary.tools.SetStatusBarColor

@Composable
fun BaseBottomBarScreen(
    title: String = "",
    showTop: Boolean = false,
    modifier: Modifier = Modifier,
    toolbarColor: Color = MaterialTheme.colors.background,
    toolbarIconsLight: Boolean = MaterialTheme.colors.isLight,
    navigationColor: Color = MaterialTheme.colors.background,
    navigationIconsLight: Boolean = MaterialTheme.colors.isLight,
    tabs: List<BaseTab>
) {
    SetStatusBarColor(toolbarColor, toolbarIconsLight)
    SetNavigationBarColor(navigationColor, navigationIconsLight)

    TabNavigator(tabs.first(), tabDisposable = {
        TabDisposable(
            it,
            tabs,
        )
    }) {
        ScreenContent(
            modifier,
            title,
            showTop,
            toolbarColor,
            toolbarIconsLight,
            tabs
        ) {
            CurrentTab()
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    title: String,
    showTop: Boolean,
    toolbarColor: Color,
    toolbarIconsLight: Boolean,
    tabs: List<Tab>,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            if (title.isNotEmpty() || showTop) {
                TopAppBar(
                    backgroundColor = toolbarColor,
                    contentColor = if (toolbarIconsLight) Color.Black else Color.White,
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

        AlertView(
            title = PopupHandler.alertTitle,
            message = PopupHandler.alertMessage,
            acceptText = PopupHandler.alertAcceptText,
            dismiss = PopupHandler.alertDismiss
        )

        AlertConfirm(
            title = PopupHandler.confirmTitle,
            message = PopupHandler.confirmMessage,
            confirmText = PopupHandler.confirmAcceptText,
            cancelText = PopupHandler.confirmCancelText,
            dismiss = PopupHandler.confirmDismiss,
        ) {
            PopupHandler.confirmAccept()
        }

        AlertList(
            title = PopupHandler.listTitle,
            message = PopupHandler.listMessage,
            options = PopupHandler.listOptions,
            show = PopupHandler.listShow,
            confirmText = PopupHandler.listAcceptText,
            cancelText = PopupHandler.listCancelText,
            dismiss = PopupHandler.listDismiss
        ) {
            PopupHandler.listAccept(it)
        }

        AlertPrompt(
            title = PopupHandler.promptTitle,
            subtitle = PopupHandler.promptSubtitle,
            input = PopupHandler.promptInput,
            confirmText = PopupHandler.promptAcceptText,
            dismiss = PopupHandler.promptDismiss
        ) {
            PopupHandler.promptAccept(it)
        }

        AlertProgress(
            title = PopupHandler.progressTitle,
            cancelText = PopupHandler.progressCancelText
        ) {
            PopupHandler.progressDismiss()
        }
    }

    LoadingView(PopupHandler.isLoading)
}