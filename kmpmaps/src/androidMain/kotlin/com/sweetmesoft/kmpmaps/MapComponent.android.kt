package com.sweetmesoft.kmpmaps

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sweetmesoft.kmplibrary.BaseAndroid.Companion.getContext
import com.sweetmesoft.kmpmaps.objects.Coordinates
import com.sweetmesoft.kmpmaps.objects.GeoPosition
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Composable
actual fun MapComponent(
    coordinates: Coordinates,
    zoom: Float,
    zoomEnabled: Boolean,
    scrollEnabled: Boolean,
    rotateEnabled: Boolean,
    locationEnabled: Boolean,
    showPointsOfInterest: Boolean,
    showBuildings: Boolean,
    showCompass: Boolean,
    showTraffic: Boolean,
    markers: List<GeoPosition>,
    onMapClick: (coordinates: Coordinates) -> Unit,
    onMapLongClick: (coordinates: Coordinates) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val coordinate = LatLng(coordinates.latitude, coordinates.longitude)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(coordinate, zoom)
        }

        LaunchedEffect(coordinate, zoom) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    coordinate,
                    zoom
                )
            )
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = zoomEnabled,
                zoomGesturesEnabled = zoomEnabled,
                scrollGesturesEnabled = scrollEnabled,
                rotationGesturesEnabled = rotateEnabled,
                compassEnabled = showCompass,
                myLocationButtonEnabled = locationEnabled
            ),
            properties = MapProperties(
                isMyLocationEnabled = locationEnabled,
                isIndoorEnabled = showPointsOfInterest,
                isBuildingEnabled = showBuildings,
                isTrafficEnabled = showTraffic
            ),
            mapColorScheme = if (MaterialTheme.colors.isLight) ComposeMapColorScheme.LIGHT else ComposeMapColorScheme.DARK,
            onMapClick = { latLng ->
                onMapClick(Coordinates(latLng.latitude, latLng.longitude))
            },
            onMapLongClick = { latLng ->
                onMapLongClick(Coordinates(latLng.latitude, latLng.longitude))
            }
        ) {
            markers.forEach { marker ->
                val markerCoordinate =
                    LatLng(marker.coordinates.latitude, marker.coordinates.longitude)
                if (marker.markerMap.isVisible) {
                    Marker(
                        state = MarkerState(
                            position = markerCoordinate
                        ),
                        title = marker.markerMap.title,
                        snippet = marker.markerMap.snippet,
                        icon = createCustomMarkerIcon(
                            color = marker.markerMap.iconColor.toArgb()
                        ),
                        onClick = { marker.markerMap.onClick(marker) },
                        onInfoWindowClick = { marker.markerMap.onInfoWindowClick(marker) }
                    )
                }

                if (marker.circleMap.radius != 0.0) {
                    Circle(
                        center = coordinate,
                        radius = marker.circleMap.radius,
                        strokeColor = marker.circleMap.strokeColor,
                        strokeWidth = marker.circleMap.strokeWidth,
                        fillColor = marker.circleMap.fillColor
                    )
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
actual suspend fun getLocation(): Coordinates = suspendCancellableCoroutine { cont ->
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(getContext())
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            cont.resume(Coordinates(location.latitude, location.longitude))
        } else {
            cont.resumeWithException(Exception("Unable to get location"))
        }
    }.addOnFailureListener { exception ->
        cont.resumeWithException(exception)
    }
}

private fun createCustomMarkerIcon(
    color: Int
): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(getContext(), R.drawable.pin)?.mutate()
    val alpha = (255 * 0.8f).toInt()
    val transparentMarkerColor =
        Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    drawable?.setTint(transparentMarkerColor)
    val bitmap = Bitmap.createBitmap(
        drawable!!.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}