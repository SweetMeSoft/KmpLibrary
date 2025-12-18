package com.sweetmesoft.kmptestapp.screens.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseDrawerScreen
import com.sweetmesoft.kmpbase.base.BaseViewModel
import com.sweetmesoft.kmptestapp.tabs.FavoritesTab
import com.sweetmesoft.kmptestapp.tabs.HomeTab
import com.sweetmesoft.kmptestapp.tabs.SettingsTab

class DrawerScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = BaseViewModel.navigator
        BaseDrawerScreen(
            tabs = listOf(HomeTab(), FavoritesTab(), SettingsTab()),
            logoutAction = { navigator.pop() },
            headerView = {
                Box(
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Drawer Header")
                }
            })
    }
}
