package com.sweetmesoft.kmptestapp.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.base.BaseViewModel.Companion.navigator
import com.sweetmesoft.kmpbase.controls.commonList.LocalGridList
import com.sweetmesoft.kmpbase.objects.IconAction
import com.sweetmesoft.kmptestapp.screens.about.AboutScreen
import com.sweetmesoft.kmptestapp.screens.bottombar.BottomBarScreen
import com.sweetmesoft.kmptestapp.screens.colors.ColorsScreen
import com.sweetmesoft.kmptestapp.screens.controls.ControlsScreen
import com.sweetmesoft.kmptestapp.screens.dialogs.DialogsScreen
import com.sweetmesoft.kmptestapp.screens.drawer.DrawerScreen
import com.sweetmesoft.kmptestapp.screens.map.MapScreen
import com.sweetmesoft.kmptestapp.screens.material.MaterialScreen
import com.sweetmesoft.kmptestapp.screens.network.NetworkScreen
import com.sweetmesoft.kmptestapp.screens.pickers.PickersScreen
import compose.icons.TablerIcons
import compose.icons.tablericons.Adjustments
import compose.icons.tablericons.Calendar
import compose.icons.tablericons.Container
import compose.icons.tablericons.InfoCircle
import compose.icons.tablericons.LayoutBottombar
import compose.icons.tablericons.LayoutSidebar
import compose.icons.tablericons.Map
import compose.icons.tablericons.Message
import compose.icons.tablericons.Palette
import compose.icons.tablericons.World

data class MainMenuItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

class MainScreen : Screen {
    @Suppress("RequiresFeature")
    @Composable
    override fun Content() {
        remember { MainViewModel() }
        BaseScreen(
            showTop = true, title = "KMP Library", iconActions = listOf(
                IconAction(TablerIcons.InfoCircle, "About", true) {
                    navigator.push(AboutScreen())
                })
        ) {
            val items = listOf(
                MainMenuItem(
                    "Pickers",
                    "Select dates and times easily",
                    TablerIcons.Calendar,
                    Color(0xFFE91E63)
                ) { navigator.push(PickersScreen()) }, MainMenuItem(
                    "Dialogs", "Alerts, confirms and more", TablerIcons.Message, Color(0xFF2196F3)
                ) { navigator.push(DialogsScreen()) }, MainMenuItem(
                    "Colors", "Theme Color Palette", TablerIcons.Palette, Color(0xFF607D8B)
                ) { navigator.push(ColorsScreen()) }, MainMenuItem(
                    "M3 Default",
                    "Standard Material 3 Components",
                    TablerIcons.Container,
                    Color(0xFF673AB7)
                ) { navigator.push(MaterialScreen()) }, MainMenuItem(
                    "Maps", "Display maps and routes", TablerIcons.Map, Color(0xFF4CAF50)
                ) { navigator.push(MapScreen()) }, MainMenuItem(
                    "Network", "Test HTTP Methods", TablerIcons.World, Color(0xFF009688)
                ) { navigator.push(NetworkScreen()) }, MainMenuItem(
                    "Controls", "Test KMP Controls", TablerIcons.Adjustments, Color(0xFF9C27B0)
                ) { navigator.push(ControlsScreen()) }, MainMenuItem(
                    "Bottom Bar",
                    "Test Bottom Bar Screen",
                    TablerIcons.LayoutBottombar,
                    Color(0xFFFF9800)
                ) { navigator.push(BottomBarScreen()) }, MainMenuItem(
                    "Drawer", "Test Drawer Screen", TablerIcons.LayoutSidebar, Color(0xFF673AB7)
                ) { navigator.push(DrawerScreen()) }, MainMenuItem(
                    "About", "Learn more about this app", TablerIcons.InfoCircle, Color(0xFFFF9800)
                ) { navigator.push(AboutScreen()) })

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Text(
                    text = "KMP Library",
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Try all our Free KMP Components.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                LocalGridList(
                    list = items,
                    columns = 2,
                    modifier = Modifier.fillMaxSize(),
                    spaceBetween = 12.dp
                ) { _, item -> MenuItemCard(item) }
            }
        }
    }

    @Composable
    private fun MenuItemCard(item: MainMenuItem) {
        Card(modifier = Modifier.fillMaxHeight().clickable { item.onClick() }) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize().padding(12.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = item.color,
                    modifier = Modifier.size(28.dp)
                )

                Column {
                    Text(
                        text = item.title, style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
