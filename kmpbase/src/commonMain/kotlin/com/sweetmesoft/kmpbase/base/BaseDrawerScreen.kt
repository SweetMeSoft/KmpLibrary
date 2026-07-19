package com.sweetmesoft.kmpbase.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import dev.seyfarth.tablericons.outlined.ChevronRight
import dev.seyfarth.tablericons.outlined.Logout
import dev.seyfarth.tablericons.outlined.Menu2
import kmplibrary.kmpbase.generated.resources.Logout
import kmplibrary.kmpbase.generated.resources.Res
import kmplibrary.kmpbase.generated.resources.header_drawer
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource

class BaseDrawerScreen {
    companion object {
        var currentTab: MutableState<Int> = mutableStateOf(0)
        var selectedTab: MutableState<BaseTab?> = mutableStateOf(null)
    }
}

@Composable
fun BaseDrawerScreen(
    tabs: List<BaseTab>,
    modifier: Modifier = Modifier,
    vm: BaseViewModel = BaseViewModel(),
    logoutAction: () -> Unit = {},
    headerHeight: Dp = 150.dp,
    headerBackground: @Composable (() -> Unit)? = {
        Image(
            painter = painterResource(Res.drawable.header_drawer),
            contentDescription = "Header Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    },
    headerView: @Composable () -> Unit = {}
) {
    val firstSelectable = remember(tabs) {
        tabs.firstOrNull { it.subTabs.isEmpty() } 
            ?: tabs.firstOrNull()?.subTabs?.firstOrNull() 
            ?: tabs.firstOrNull()
    }
    
    if (BaseDrawerScreen.selectedTab.value == null && firstSelectable != null) {
        BaseDrawerScreen.selectedTab.value = firstSelectable
        val parentIndex = tabs.indexOfFirst { it == firstSelectable || it.subTabs.contains(firstSelectable) }
        if (parentIndex != -1) {
            BaseDrawerScreen.currentTab.value = parentIndex
        }
    }

    ModalNavigationDrawer(drawerState = vm.baseState.drawerState, drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = Color.Transparent,
            drawerTonalElevation = 0.dp,
            windowInsets = WindowInsets(0, 0, 0, 0),
        ) {
            DrawerContent(tabs, vm, logoutAction, headerView, headerBackground, headerHeight)
        }
    }, content = {
        val activeTab = BaseDrawerScreen.selectedTab.value ?: firstSelectable
        if (activeTab != null) {
            ScreenContent(
                modifier, activeTab, vm
            )
        }
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
        onToolbarColor = tab.baseOptions.onToolbarColor,
        statusDarkIcons = tab.baseOptions.statusDarkIcons,
        navigationDarkIcons = tab.baseOptions.navigationDarkIcons,
        navigationColor = tab.baseOptions.navigationColor,
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
    headerView: @Composable () -> Unit,
    headerBackground: @Composable (() -> Unit)?,
    headerHeight: Dp
) {
    val scope = rememberCoroutineScope()
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
    
    val activeSubTab = BaseDrawerScreen.selectedTab.value
    if (activeSubTab != null) {
        val activeParentIndex = list.indexOfFirst { it.subTabs.contains(activeSubTab) }
        if (activeParentIndex != -1 && expandedStates[activeParentIndex] == null) {
            expandedStates[activeParentIndex] = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.78f)
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (headerBackground != null) Modifier.height(headerHeight) else Modifier
                )
        ) {
            if (headerBackground != null) {
                Box(modifier = Modifier.matchParentSize()) {
                    headerBackground()
                }
            }
            
            Box(
                modifier = Modifier
                    .then(
                        if (headerBackground != null) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
                    )
                    .padding(
                        top = if (headerBackground != null) 24.dp else 16.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                headerView()
            }
        }
        
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        )
        
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
        ) {
            list.forEachIndexed { index, item ->
                val hasChildren = item.subTabs.isNotEmpty()
                val isExpanded = expandedStates[index] ?: false
                
                val isParentOfActive = activeSubTab != null && item.subTabs.contains(activeSubTab)
                val isActive = (!hasChildren && (BaseDrawerScreen.selectedTab.value == item || (BaseDrawerScreen.selectedTab.value == null && BaseDrawerScreen.currentTab.value == index))) || (hasChildren && isParentOfActive)

                Column(modifier = Modifier.fillMaxWidth()) {
                    ItemDrawer(
                        icon = item.options.icon,
                        title = item.options.title,
                        isActive = isActive,
                        hasChildren = hasChildren,
                        isExpanded = isExpanded,
                        onToggleExpand = {
                            expandedStates[index] = !isExpanded
                        }
                    ) {
                        if (!hasChildren) {
                            BaseDrawerScreen.selectedTab.value = item
                            BaseDrawerScreen.currentTab.value = index
                            scope.launch {
                                vm.baseState.drawerState.close()
                            }
                        } else {
                            expandedStates[index] = !isExpanded
                        }
                    }
                    
                    if (hasChildren) {
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 2.dp, bottom = 4.dp)
                            ) {
                                item.subTabs.forEach { subTab ->
                                    val isSubActive = BaseDrawerScreen.selectedTab.value == subTab
                                    
                                    ItemDrawerSub(
                                        icon = subTab.options.icon,
                                        title = subTab.options.title,
                                        isActive = isSubActive
                                    ) {
                                        BaseDrawerScreen.selectedTab.value = subTab
                                        BaseDrawerScreen.currentTab.value = index
                                        scope.launch {
                                            vm.baseState.drawerState.close()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
        
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        )
        
        val logoutIcon = rememberVectorPainter(TablerIcons.Outlined.Logout)
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            ItemDrawer(
                icon = logoutIcon,
                title = stringResource(Res.string.Logout),
                isActive = false,
                hasChildren = false,
                isExpanded = false,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                logoutAction()
            }
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
    onToolbarColor: Color,
    statusDarkIcons: Boolean,
    navigationDarkIcons: Boolean,
    navigationColor: Color,
    iconActions: List<IconAction>,
    vm: BaseViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    SetStatusBarColor(toolbarColor, statusDarkIcons)
    SetNavigationBarColor(navigationColor, navigationDarkIcons)
    val scope = rememberCoroutineScope()
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        if (title.isNotEmpty() || showTop) {
            CustomToolbar(
                title = title,
                iconActions = iconActions,
                toolbarColor = toolbarColor,
                onToolbarColor = onToolbarColor,
                showNavigation = true,
                navigationIcon = TablerIcons.Outlined.Menu2,
            ) {
                scope.launch {
                    vm.openDrawer()
                }
            }
        }
    }, floatingActionButton = {
        if (fabAction != emptyFunction) {
            FloatingActionButton(onClick = fabAction) {
                Icon(
                    imageVector = fabIcon,
                    contentDescription = "fabIcon",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
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
    modifier: Modifier = Modifier,
    icon: Painter?,
    title: String,
    isActive: Boolean,
    hasChildren: Boolean = false,
    isExpanded: Boolean = false,
    onToggleExpand: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (isExpanded) 90f else 0f)
    
    val containerColor = if (isActive && !hasChildren) {
        MaterialTheme.colorScheme.primaryContainer
    } else if (isActive && hasChildren) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
    } else {
        Color.Transparent
    }
    
    val contentColor = if (isActive) {
        if (hasChildren) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    
    val iconTint = if (isActive) {
        if (hasChildren) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    }
    
    val fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                tint = iconTint,
                contentDescription = title,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }
        
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = contentColor,
            fontWeight = fontWeight,
            fontSize = 15.sp
        )
        
        if (hasChildren) {
            val chevronIcon = rememberVectorPainter(TablerIcons.Outlined.ChevronRight)
            Icon(
                painter = chevronIcon,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier
                    .size(18.dp)
                    .rotate(rotation)
            )
        }
    }
}

@Composable
private fun ItemDrawerSub(
    modifier: Modifier = Modifier,
    icon: Painter?,
    title: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isActive) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
    } else {
        Color.Transparent
    }
    
    val contentColor = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
    }
    
    val iconTint = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    }
    
    val fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(containerColor)
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                tint = iconTint,
                contentDescription = title,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
        } else {
            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .size(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
            )
            Spacer(modifier = Modifier.width(18.dp))
        }
        
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = contentColor,
            fontWeight = fontWeight,
            fontSize = 14.sp
        )
    }
}