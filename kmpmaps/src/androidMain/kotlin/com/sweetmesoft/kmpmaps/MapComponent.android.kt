package com.sweetmesoft.kmpmaps

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
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
                compassEnabled = showCompass
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
            }
        ) {
            markers.forEach {
                val markerCoordinate = LatLng(it.coordinates.latitude, it.coordinates.longitude)
                if (it.markerMap.isVisible) {
                    Marker(
                        state = MarkerState(
                            position = markerCoordinate
                        ),
                        title = it.markerMap.title,
                        snippet = it.markerMap.snippet
                    )
                }

                if (it.circleMap.radius != 0.0) {
                    Circle(
                        center = coordinate,
                        radius = it.circleMap.radius,
                        strokeColor = it.circleMap.strokeColor,
                        strokeWidth = it.circleMap.strokeWidth,
                        fillColor = it.circleMap.fillColor
                    )
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
actual suspend fun getLocation(): Coordinates = suspendCancellableCoroutine { cont ->
    val context = BaseAndroid.getContext()
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
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