package base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import controls.LoadingView
import tools.SetStatusBarColor

private val emptyFunction: () -> Unit = {}

@Composable
fun BaseScreen(
    title: String = "",
    showTop: Boolean = false,
    modifier: Modifier = Modifier,
    tabs: List<Tab> = listOf(),
    fabAction: () -> Unit = emptyFunction,
    fabIcon: ImageVector = Icons.Default.Check,
    vm: BaseViewModel = BaseViewModel(),
    content: @Composable () -> Unit
) {
    val statusBarColor =
        if (MaterialTheme.colors.isLight) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    SetStatusBarColor(statusBarColor, false)
    if (tabs.any()) {
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
                tabs,
                fabAction,
                fabIcon,
                vm
            )
        }
    } else {
        ScreenContent(
            content,
            modifier,
            title,
            showTop,
            tabs,
            fabAction,
            fabIcon,
            vm
        )
    }
}

@Composable
private fun ScreenContent(
    content: @Composable () -> Unit,
    modifier: Modifier,
    title: String,
    showTop: Boolean,
    tabs: List<Tab>,
    fabAction: () -> Unit,
    fabIcon: ImageVector,
    vm: BaseViewModel
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            if (title.isNotEmpty() || showTop) {
                TopAppBar(
                    elevation = 0.dp,
                    title = {
                        Text(title)
                    },
                    navigationIcon = {
                        if (BaseViewModel.navigator.canPop) {
                            IconButton(
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                onClick = { BaseViewModel.navigator.pop() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (tabs.any()) {
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
        content()
    }

    LoadingView(vm.baseState.isLoading)
}