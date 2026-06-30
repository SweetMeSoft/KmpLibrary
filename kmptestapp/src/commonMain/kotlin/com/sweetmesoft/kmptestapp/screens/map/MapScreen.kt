package com.sweetmesoft.kmptestapp.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.controls.alerts.PopupHandler
import com.sweetmesoft.kmpmaps.MapComponent
import com.sweetmesoft.kmpmaps.controls.CircleMap
import com.sweetmesoft.kmpmaps.controls.Coordinates
import com.sweetmesoft.kmpmaps.controls.GeoPosition
import com.sweetmesoft.kmpmaps.controls.MarkerMap
import com.sweetmesoft.kmpmaps.controls.RouteMap
import kotlinx.coroutines.launch
import kotlin.random.Random

class MapScreen : Screen {
    @Composable
    override fun Content() {
        remember { MapViewModel() }
        val scope = rememberCoroutineScope()

        // Center map in Bogota
        val defaultCenter = Coordinates(4.7110, -74.0721)

        // Predefined initial markers
        val initialMarkers = remember {
            listOf(
                GeoPosition(
                    coordinates = Coordinates(4.7110, -74.0721),
                    address = "Bogota Center",
                    markerMap = MarkerMap(
                        isVisible = true,
                        title = "Bogota Center",
                        snippet = "Red marker with 300m circle",
                        iconColor = Color.Red,
                        onInfoWindowClick = { geo ->
                            scope.launch {
                                PopupHandler.displayAlert("Info", "Info window clicked at Red Marker!")
                            }
                        }
                    ),
                    circleMap = CircleMap(
                        radius = 300.0,
                        fillColor = Color(0x33FF0000),
                        strokeColor = Color.Red,
                        strokeWidth = 2f
                    )
                ),
                GeoPosition(
                    coordinates = Coordinates(4.7150, -74.0721),
                    address = "North Point",
                    markerMap = MarkerMap(
                        isVisible = true,
                        title = "North Point",
                        snippet = "Blue marker with 150m circle",
                        iconColor = Color.Blue,
                        onInfoWindowClick = { geo ->
                            scope.launch {
                                PopupHandler.displayAlert("Info", "Info window clicked at Blue Marker!")
                            }
                        }
                    ),
                    circleMap = CircleMap(
                        radius = 150.0,
                        fillColor = Color(0x330000FF),
                        strokeColor = Color.Blue,
                        strokeWidth = 2f
                    )
                ),
                GeoPosition(
                    coordinates = Coordinates(4.7080, -74.0680),
                    address = "Southeast Point",
                    markerMap = MarkerMap(
                        isVisible = true,
                        title = "Southeast Point",
                        snippet = "Green marker",
                        iconColor = Color.Green,
                        onInfoWindowClick = { geo ->
                            scope.launch {
                                PopupHandler.displayAlert("Info", "Info window clicked at Green Marker!")
                            }
                        }
                    )
                ),
                GeoPosition(
                    coordinates = Coordinates(4.7110, -74.0780),
                    address = "West Point",
                    markerMap = MarkerMap(
                        isVisible = true,
                        title = "West Point",
                        snippet = "Magenta marker",
                        iconColor = Color.Magenta,
                        onInfoWindowClick = { geo ->
                            scope.launch {
                                PopupHandler.displayAlert("Info", "Info window clicked at Magenta Marker!")
                            }
                        }
                    )
                )
            )
        }

        var markers by remember { mutableStateOf(initialMarkers) }

        // Route connecting all current markers
        val routes = remember(markers) {
            listOf(
                RouteMap(
                    points = markers.map { it.coordinates },
                    color = Color.DarkGray,
                    width = 8f
                )
            )
        }

        BaseScreen(
            title = "Maps Demo",
            showTop = true
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MapComponent(
                    coordinates = defaultCenter,
                    zoom = 15f,
                    zoomEnabled = true,
                    scrollEnabled = true,
                    rotateEnabled = true,
                    routes = routes,
                    markers = markers,
                    onMapClick = { coord ->
                        // Add a dynamic user marker with a random color
                        val randomColor = Color(
                            red = Random.nextFloat(),
                            green = Random.nextFloat(),
                            blue = Random.nextFloat(),
                            alpha = 1.0f
                        )
                        val newMarker = GeoPosition(
                            coordinates = coord,
                            address = "User Marker",
                            markerMap = MarkerMap(
                                isVisible = true,
                                title = "User Point",
                                snippet = "Lat: ${coord.latitude.toString().take(6)}, Lng: ${coord.longitude.toString().take(6)}",
                                iconColor = randomColor,
                                onInfoWindowClick = { geo ->
                                    scope.launch {
                                        PopupHandler.displayAlert(
                                            "User Marker Click",
                                            "Details:\nLatitude: ${geo.coordinates.latitude}\nLongitude: ${geo.coordinates.longitude}"
                                        )
                                    }
                                }
                            )
                        )
                        markers = markers + newMarker
                    },
                    onMapLongClick = {
                        // Reset/Clear user-added markers
                        markers = initialMarkers
                        scope.launch {
                            PopupHandler.displayAlert("Reset Map", "User markers cleared, map reset to default points.")
                        }
                    }
                )
            }
        }
    }
}
