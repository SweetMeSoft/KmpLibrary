package com.sweetmesoft.kmplibrary.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Bars
import compose.icons.fontawesomeicons.solid.SignOutAlt
import com.sweetmesoft.kmplibrary.controls.commonList.LocalList
import kotlinx.coroutines.launch
import tools.SetNavigationBarColor
import tools.SetStatusBarColor

class BaseDrawerScreen {
    companion object {
        var currentTab: MutableState<Int> = mutableStateOf(0)
    }
}

@Composable
fun BaseDrawerScreen(
    tabs: List<Tab>,
    modifier: Modifier = Modifier,
    vm: BaseViewModel = BaseViewModel(),
    toolbarColor: Color = MaterialTheme.colors.background,
    navigationColor: Color = MaterialTheme.colors.background,
    logoutAction: () -> Unit = {}
) {
    SetStatusBarColor(toolbarColor, MaterialTheme.colors.isLight)
    SetNavigationBarColor(navigationColor, MaterialTheme.colors.isLight)

    ModalDrawer(
        drawerBackgroundColor = Color.Transparent,
        drawerElevation = 0.dp,
        drawerContent = {
            DrawerContent(tabs, vm, logoutAction)
        }, drawerState = vm.baseState.drawerState
    ) {
        ScreenContent(
            modifier, tabs[BaseDrawerScreen.currentTab.value], toolbarColor, vm
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    tab: Tab,
    toolbarColor: Color,
    vm: BaseViewModel
) {
    val scope = rememberCoroutineScope()
    Scaffold(modifier = modifier.fillMaxSize().background(MaterialTheme.colors.background),
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = toolbarColor,
                title = {
                    Text(tab.options.title)
                },
                navigationIcon = {
                    IconButton(modifier = Modifier.padding(horizontal = 16.dp), onClick = {
                        scope.launch {
                            vm.openDrawer()
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = FontAwesomeIcons.Solid.Bars,
                            contentDescription = "List"
                        )
                    }
                }
            )
        }
    ) {
        tab.Content()
    }
}

@Composable
private fun DrawerContent(list: List<Tab>, vm: BaseViewModel, logoutAction: () -> Unit) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f)
            .background(MaterialTheme.colors.surface)
    ) {
        //TODO Add parametrizable header view
//        UserProfileView(
//            name = vm.settings[Constants.KEY_CURRENT_USER_NAME, ""],
//            email = vm.settings[Constants.KEY_CURRENT_USER_EMAIL, "Invitado"],
//            photoUrl = vm.settings[Constants.KEY_CURRENT_USER_PHOTO_URL, "Ingresa con tu cuenta de Google"]
//        )
        Box(modifier = Modifier.weight(1f)) {
            LocalList(
                list = list,
            ) { index, item ->
                Column(
                    modifier = Modifier.background(
                        if (BaseDrawerScreen.currentTab.value == index) MaterialTheme.colors.secondary else Color.Transparent
                    )
                ) {
                    ItemDrawer(
                        icon = item.options.icon, title = item.options.title
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
        val icon = rememberVectorPainter(FontAwesomeIcons.Solid.SignOutAlt);
        ItemDrawer(
            icon = icon, title = "Salir"
        ) {
            logoutAction()
        }
    }
}

@Composable
private fun ItemDrawer(
    modifier: Modifier = Modifier, icon: Painter?, title: String, onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable {
            onClick()
        }.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon!!,
            tint = MaterialTheme.colors.onSurface,
            contentDescription = title,
            modifier = Modifier.padding(end = 16.dp).size(30.dp)
        )
        Text(
            text = title, modifier = Modifier.weight(1f), color = MaterialTheme.colors.onSurface
        )
    }
}