package com.sweetmesoft.kmptestapp.screens.bottombar

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseBottomBarScreen
import com.sweetmesoft.kmptestapp.tabs.FavoritesTab
import com.sweetmesoft.kmptestapp.tabs.HomeTab
import com.sweetmesoft.kmptestapp.tabs.SettingsTab

class BottomBarScreen : Screen {
    @Composable
    override fun Content() {
        BaseBottomBarScreen(tabs = listOf(HomeTab(), FavoritesTab(), SettingsTab()))
    }
}
