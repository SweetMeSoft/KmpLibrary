package com.sweetmesoft.kmplibrary.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmplibrary.controls.LoadingView
import com.sweetmesoft.kmplibrary.controls.alerts.AlertConfirm
import com.sweetmesoft.kmplibrary.controls.alerts.AlertList
import com.sweetmesoft.kmplibrary.controls.alerts.AlertProgress
import com.sweetmesoft.kmplibrary.controls.alerts.AlertPrompt
import com.sweetmesoft.kmplibrary.controls.alerts.AlertView
import com.sweetmesoft.kmplibrary.controls.commonList.LocalList
import com.sweetmesoft.kmplibrary.objects.IconAction
import com.sweetmesoft.kmplibrary.tools.SetStatusBarColor
import com.sweetmesoft.kmplibrary.tools.emptyFunction
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronRight
import compose.icons.tablericons.Logout
import compose.icons.tablericons.Menu2
import kmplibrary.library.generated.resources.Logout
import kmplibrary.library.generated.resources.Res
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

class BaseDrawerScreen {
    companion object {
        var currentTab: MutableState<Int> = mutableStateOf(0)
    }
}

@Composable
fun BaseDrawerScreen(
    tabs: List<BaseTab>,
    modifier: Modifier = Modifier,
    vm: BaseViewModel = BaseViewModel(),
    logoutAction: () -> Unit = {},
    headerView: @Composable () -> Unit = {}
) {
    ModalDrawer(
        drawerBackgroundColor = Color.Transparent,
        drawerElevation = 0.dp,
        drawerContent = {
            DrawerContent(tabs, vm, logoutAction, headerView)
        }, drawerState = vm.baseState.drawerState
    ) {
        ScreenContent(
            modifier, tabs[BaseDrawerScreen.currentTab.value], vm
        )
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier,
    tab: BaseTab,
    vm: BaseViewModel
) {
    TabContent(
        modifier,
        title = tab.baseOptions.title,
        showTop = tab.baseOptions.showTop,
        fabAction = tab.baseOptions.fabAction,
        fabIcon = tab.baseOptions.fabIcon,
        toolbarColor = tab.baseOptions.toolbarColor,
        toolbarIconsLight = tab.baseOptions.toolbarIconsLight,
        iconActions = tab.baseOptions.iconActions,
        vm = vm
    ) {
        tab.Content()

        AlertView()
        AlertConfirm()
        AlertList()
        AlertPrompt()
        AlertProgress()
    }

    LoadingView()
}

@Composable
private fun DrawerContent(
    list: List<BaseTab>,
    vm: BaseViewModel,
    logoutAction: () -> Unit,
    headerView: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f)
            .background(MaterialTheme.colors.surface)
    ) {
        headerView()
        Box(modifier = Modifier.weight(1f)) {
            LocalList(
                list = list,
            ) { index, item ->
                Column {
                    ItemDrawer(
                        icon = item.options.icon, title = item.options.title, index = index
                    ) {
                        vm.updateTab(index)
                        scope.launch {
                            vm.baseState.drawerState.close()
                        }
                    }
                    Divider()
                }
            }
        }
        val icon = rememberVectorPainter(TablerIcons.Logout)
        ItemDrawer(
            icon = icon, title = stringResource(Res.string.Logout), index = 9999
        ) {
            logoutAction()
        }
    }
}

@Composable
private fun TabContent(
    modifier: Modifier,
    title: String,
    showTop: Boolean,
    fabAction: () -> Unit,
    fabIcon: ImageVector,
    toolbarColor: Color,
    toolbarIconsLight: Boolean,
    iconActions: List<IconAction> = listOf(),
    vm: BaseViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    SetStatusBarColor(color = toolbarColor, darkIcons = toolbarIconsLight)
    val scope = rememberCoroutineScope()
    Scaffold(
        contentWindowInsets = WindowInsets.safeContent,
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
                    navigationIcon = {
                        IconButton(modifier = Modifier.padding(horizontal = 16.dp), onClick = {
                            scope.launch {
                                vm.openDrawer()
                            }
                        }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = TablerIcons.Menu2,
                                contentDescription = "List"
                            )
                        }
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
    }
}

@Composable
private fun ItemDrawer(
    modifier: Modifier = Modifier, icon: Painter?, index: Int, title: String, onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable {
            onClick()
        }.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (BaseDrawerScreen.currentTab.value == index) {
            Icon(
                imageVector = TablerIcons.ChevronRight,
                tint = MaterialTheme.colors.onSurface,
                contentDescription = title,
                modifier = Modifier.padding(end = 16.dp).size(16.dp)
            )
        }
        Icon(
            painter = icon!!,
            tint = MaterialTheme.colors.onSurface,
            contentDescription = title,
            modifier = Modifier.padding(end = 16.dp).size(24.dp)
        )
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.onSurface,
            fontWeight = if (BaseDrawerScreen.currentTab.value == index) FontWeight.Bold else FontWeight.Normal
        )
    }
}