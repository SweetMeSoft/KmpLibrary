package com.sweetmesoft.kmpbase.base

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
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.sweetmesoft.kmpbase.controls.LoadingView
import com.sweetmesoft.kmpbase.controls.alerts.AlertConfirm
import com.sweetmesoft.kmpbase.controls.alerts.AlertList
import com.sweetmesoft.kmpbase.controls.alerts.AlertProgress
import com.sweetmesoft.kmpbase.controls.alerts.AlertPrompt
import com.sweetmesoft.kmpbase.controls.alerts.AlertView
import com.sweetmesoft.kmpbase.controls.commonList.LocalList
import com.sweetmesoft.kmpbase.objects.IconAction
import com.sweetmesoft.kmpbase.tools.SetStatusBarColor
import com.sweetmesoft.kmpbase.tools.emptyFunction
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronRight
import compose.icons.tablericons.Logout
import compose.icons.tablericons.Menu2
import kmplibrary.kmpbase.generated.resources.Logout
import kmplibrary.kmpbase.generated.resources.Res
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
    ModalNavigationDrawer(drawerState = vm.baseState.drawerState, drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = Color.Transparent, drawerTonalElevation = 0.dp
        ) {
            DrawerContent(tabs, vm, logoutAction, headerView)
        }
    }, content = {
        ScreenContent(
            modifier, tabs[BaseDrawerScreen.currentTab.value], vm
        )
    })
}

@Composable
fun ScreenContent(
    modifier: Modifier, tab: BaseTab, vm: BaseViewModel
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
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.safeDrawing),
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
                    HorizontalDivider()
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

@OptIn(ExperimentalMaterial3Api::class)
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
                }, navigationIcon = {
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
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = toolbarColor,
                    titleContentColor = if (toolbarIconsLight) Color.Black else Color.White,
                    navigationIconContentColor = if (toolbarIconsLight) Color.Black else Color.White,
                    actionIconContentColor = if (toolbarIconsLight) Color.Black else Color.White
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
            content(it)
        }
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
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = title,
                modifier = Modifier.padding(end = 16.dp).size(16.dp)
            )
        }
        Icon(
            painter = icon!!,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = title,
            modifier = Modifier.padding(end = 16.dp).size(24.dp)
        )
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = if (BaseDrawerScreen.currentTab.value == index) FontWeight.Bold else FontWeight.Normal
        )
    }
}