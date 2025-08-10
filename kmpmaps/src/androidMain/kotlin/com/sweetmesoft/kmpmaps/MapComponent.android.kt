package com.sweetmesoft.kmpmaps

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Looper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.UrlTileProvider
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.rememberCameraPositionState
import com.sweetmesoft.kmpcontrols.tools.BaseAndroid.Companion.getContext
import com.sweetmesoft.kmpmaps.controls.Coordinates
import com.sweetmesoft.kmpmaps.controls.GeoPosition
import kotlinx.coroutines.suspendCancellableCoroutine
import java.net.URL
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
            mapColorScheme = if (!isSystemInDarkTheme()) ComposeMapColorScheme.LIGHT else ComposeMapColorScheme.DARK,
            onMapClick = { latLng ->
                onMapClick(Coordinates(latLng.latitude, latLng.longitude))
            },
            onMapLongClick = { latLng ->
                onMapLongClick(Coordinates(latLng.latitude, latLng.longitude))
            }
        ) {
            val tileProvider = object : UrlTileProvider(256, 256) {
                override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
                    return try {
                        URL("https://mi-servidor-de-tiles/$zoom/$x/$y.png")
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            TileOverlay(
                tileProvider = tileProvider,
                fadeIn = true,
                transparency = 0.5f
            )

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
actual suspend fun getLocation(updateLocation: Boolean): Coordinates =
    suspendCancellableCoroutine { cont ->
        if (updateLocation) {
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(getContext())
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 15000)
                .setWaitForAccurateLocation(true)
                .setMaxUpdates(1)
                .build()
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        cont.resume(Coordinates(location.latitude, location.longitude))
                    } else {
                        cont.resumeWithException(Exception("Unable to get location"))
                    }
                    fusedLocationClient.removeLocationUpdates(this)
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
//                    if (!locationAvailability.isLocationAvailable) {
//                        cont.resumeWithException(Exception("Location not available"))
//                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(getContext())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    cont.resume(Coordinates(location.latitude, location.longitude))
                } else {
                    cont.resumeWithException(Exception("Unable to get location stored"))
                }
            }.addOnFailureListener { exception ->
                cont.resumeWithException(exception)
            }
        }
    }

private fun createCustomMarkerIcon(
    color: Int
): BitmapDescriptor {
    val drawable =
        ContextCompat.getDrawable(getContext(), R.drawable.pin)?.mutate()
    drawable?.setTint(color)
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