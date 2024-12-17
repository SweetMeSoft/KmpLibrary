package com.sweetmesoft.kmpmaps

import androidx.compose.runtime.Composable
import com.sweetmesoft.kmpmaps.objects.Coordinates
import com.sweetmesoft.kmpmaps.objects.GeoPosition
import kotlin.math.log2

@Composable
expect fun MapComponent(
    coordinates: Coordinates,
    zoom: Float = 1f,
    zoomEnabled: Boolean = true,
    scrollEnabled: Boolean = true,
    rotateEnabled: Boolean = false,
    locationEnabled: Boolean = false,
    showPointsOfInterest: Boolean = false,
    showBuildings: Boolean = false,
    showCompass: Boolean = false,
    showTraffic: Boolean = false,
    markers: List<GeoPosition> = emptyList(),
    onMapClick: (Coordinates) -> Unit = {},
    onMapLongClick: (Coordinates) -> Unit = {}
)

expect suspend fun getLocation(): Coordinates

fun calculateDynamicZoomLevel(radiusInMeters: Double): Float {
    val earthCircumferenceInMeters = 40_075_000.0
    val adjustmentFactor = 1.8
    return (log2(earthCircumferenceInMeters / (radiusInMeters * adjustmentFactor))).toFloat()
}

fun calculateDynamicZoomLevel(radiusInMeters: Float): Float {
    return calculateDynamicZoomLevel(radiusInMeters.toDouble())
}

fun calculateDynamicZoomLevel(radiusInMeters: Int): Float {
    return calculateDynamicZoomLevel(radiusInMeters.toDouble())
}