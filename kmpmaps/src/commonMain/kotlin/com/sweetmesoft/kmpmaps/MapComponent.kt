package com.sweetmesoft.kmpmaps

import androidx.compose.runtime.Composable
import com.sweetmesoft.kmplibrary.tools.toRadians
import com.sweetmesoft.kmpmaps.objects.Coordinates
import com.sweetmesoft.kmpmaps.objects.GeoPosition
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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

private fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 6371.0
    val dLat = (lat2 - lat1).toRadians()
    val dLon = (lon2 - lon1).toRadians()
    val a =
        sin(dLat / 2).pow(2) + cos(lat1.toRadians()) * cos(lat2.toRadians()) * sin(dLon / 2).pow(
            2
        )
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}