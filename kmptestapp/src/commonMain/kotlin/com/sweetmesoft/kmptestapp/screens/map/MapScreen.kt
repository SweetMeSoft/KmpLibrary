package com.sweetmesoft.kmptestapp.screens.map

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmplibrary.base.BaseScreen
import com.sweetmesoft.kmpmaps.MapComponent
import com.sweetmesoft.kmpmaps.controls.Coordinates
import com.sweetmesoft.kmpmaps.controls.RouteMap

class MapScreen : Screen {
    @Composable
    override fun Content() {
        remember { MapViewModel() }
        BaseScreen(
            toolbarColor = MaterialTheme.colorScheme.primary,
            toolbarIconsLight = false,
            title = "Map",
            edgeToEdge = false
        ) {
            MapComponent(
                coordinates = Coordinates(4.7, -74.12), zoom = 15f, routes = listOf(
                    RouteMap(
                        points = List(10) {
                            Coordinates(
                                latitude = 4.7 + ((0..10).random() - 0.5) * 0.1,
                                longitude = -74.12 + ((0..10).random() - 0.5) * 0.1
                            )
                        }, color = Color.Red, width = 5f
                    ), RouteMap(
                        points = List(10) {
                            Coordinates(
                                latitude = 4.8 + ((0..10).random() - 0.5) * 0.1,
                                longitude = -74.5 + ((0..10).random() - 0.5) * 0.1
                            )
                        }, color = Color.Green, width = 5f
                    )
                )
            )
        }
    }
}
