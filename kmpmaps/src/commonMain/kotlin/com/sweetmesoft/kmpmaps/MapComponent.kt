package com.sweetmesoft.kmpmaps

import androidx.compose.runtime.Composable
import com.sweetmesoft.kmpcontrols.utils.toRadians
import com.sweetmesoft.kmpmaps.controls.Coordinates
import com.sweetmesoft.kmpmaps.controls.GeoPosition
import com.sweetmesoft.kmpmaps.controls.RouteMap
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

const val earthCircumferenceInMeters = 40_075_016.686
const val tileSize = 111_319.444
const val earthRadius = 6371.0

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
    routes: List<RouteMap> = emptyList(),
    markers: List<GeoPosition> = emptyList(),
    onMapClick: (Coordinates) -> Unit = {},
    onMapLongClick: (Coordinates) -> Unit = {}
)

expect suspend fun getLocation(updateLocation: Boolean = false): Coordinates

fun calculateZoomByRadius(radiusInMeters: Double): Float {
    val earthCircumferenceInMeters = 40_075_000.0
    val adjustmentFactor = 1.8
    return (log2(earthCircumferenceInMeters / (radiusInMeters * adjustmentFactor))).toFloat()
}

fun calculateZoomByRadius(radiusInMeters: Float): Float {
    return calculateZoomByRadius(radiusInMeters.toDouble())
}

fun calculateZoomByRadius(radiusInMeters: Int): Float {
    return calculateZoomByRadius(radiusInMeters.toDouble())
}

fun calculateDistance(from: Coordinates, to: Coordinates): Double {
    val distance = haversineDistance(
        from.latitude,
        from.longitude,
        to.latitude,
        to.longitude
    )
    return distance * 1000
}

fun calculateZoomAndCenter(positions: List<Coordinates>): Pair<Float, Coordinates> {
    if (positions.isEmpty()) throw IllegalArgumentException("La lista de posiciones no puede estar vacía.")
    val minLat = positions.minOf { it.latitude }
    val maxLat = positions.maxOf { it.latitude }
    val minLng = positions.minOf { it.longitude }
    val maxLng = positions.maxOf { it.longitude }
    val centerLat = (minLat + maxLat) / 2
    val centerLng = (minLng + maxLng) / 2
    val center = Coordinates(centerLat, centerLng)
    val latDiff = maxLat - minLat
    val lngDiff = maxLng - minLng
    val latZoom = log2(earthCircumferenceInMeters / (latDiff * tileSize))
    val lngZoom = log2(earthCircumferenceInMeters / (lngDiff * tileSize))
    val zoom = min(latZoom, lngZoom).toFloat()
    val minZoom = 1f
    val maxZoom = 18f
    val adjustedZoom = zoom.coerceIn(minZoom, maxZoom)
    return Pair(adjustedZoom, center)
}

private fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val dLat = (lat2 - lat1).toRadians()
    val dLon = (lon2 - lon1).toRadians()
    val a =
        sin(dLat / 2).pow(2) + cos(lat1.toRadians()) * cos(lat2.toRadians()) * sin(dLon / 2).pow(
            2
        )
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}