package com.sweetmesoft.kmpmaps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import com.sweetmesoft.kmpmaps.controls.Coordinates
import com.sweetmesoft.kmpmaps.controls.GeoPosition
import com.sweetmesoft.kmpmaps.controls.RouteMap
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.interpretPointed
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.useContents
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.Foundation.NSError
import platform.MapKit.MKAnnotationProtocol
import platform.MapKit.MKAnnotationView
import platform.MapKit.MKCircle
import platform.MapKit.MKCircleRenderer
import platform.MapKit.MKCoordinateRegionMakeWithDistance
import platform.MapKit.MKMapTypeStandard
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKOverlayProtocol
import platform.MapKit.MKOverlayRenderer
import platform.MapKit.MKPinAnnotationView
import platform.MapKit.MKPolyline
import platform.MapKit.addOverlay
import platform.MapKit.overlays
import platform.MapKit.removeOverlays
import platform.UIKit.UIButton
import platform.UIKit.UIButtonTypeDetailDisclosure
import platform.UIKit.UIColor
import platform.UIKit.UIControl
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.UITapGestureRecognizer
import platform.darwin.NSObject
import platform.objc.sel_registerName
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

var mapView: MKMapView = MKMapView()

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class, ExperimentalComposeUiApi::class)
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
    routes: List<RouteMap>,
    markers: List<GeoPosition>,
    onMapClick: (Coordinates) -> Unit,
    onMapLongClick: (Coordinates) -> Unit
) {
    remember {
        CLLocationCoordinate2DMake(coordinates.latitude, coordinates.longitude)
    }
    val mapDelegate = rememberMapDelegate()
    val tapGestureRecognizer = tapGestureRecognizer(onMapClick)

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            println("Se llamò el factory")
            MKMapView().apply {
                setShowsUserLocation(locationEnabled)
                setShowsPointsOfInterest(showPointsOfInterest)
                setShowsBuildings(showBuildings)
                setShowsCompass(showCompass)
                setShowsTraffic(showTraffic)
                setZoomEnabled(zoomEnabled)
                setScrollEnabled(scrollEnabled)
                setRotateEnabled(rotateEnabled)
                setMapType(MKMapTypeStandard)
                setUserInteractionEnabled(true)
                setDelegate(mapDelegate)
                mapView = this
            }
        },
        update = { mapView ->
            println("Se llamò el update")
            val padding = 32.dp.value.toDouble()
            UIEdgeInsetsMake(padding, padding, padding, padding)

            mapView.removeAnnotations(mapView.annotations)
            mapView.removeOverlays(mapView.overlays)

            routes.forEach {
                if (it.points.isNotEmpty()) {
                    memScoped {
                        val coordinates = allocArray<CLLocationCoordinate2D>(it.points.size)

                        it.points.forEachIndexed { index, coordinate ->
                            val clLocation = CLLocationCoordinate2DMake(
                                coordinate.latitude,
                                coordinate.longitude
                            )
                            val offset = index * sizeOf<CLLocationCoordinate2D>()
                            val pointer = coordinates.rawValue + offset
                            val a = interpretPointed<CLLocationCoordinate2D>(pointer).ptr
                            clLocation.place(a)
                        }

                        MKPolyline.polylineWithCoordinates(
                            coords = coordinates,
                            count = it.points.size.toULong()
                        )
                    }
                }
            }

            markers.forEach {
                if (it.markerMap.isVisible) {
                    val annotation = CustomPointAnnotation(it).apply {
                        setCoordinate(
                            CLLocationCoordinate2DMake(
                                it.coordinates.latitude,
                                it.coordinates.longitude
                            )
                        )
                        setTitle(it.markerMap.title)
                        setSubtitle(it.markerMap.snippet)
                    }
                    mapView.addAnnotation(annotation)
                }

                if (it.circleMap.radius != 0.0) {
                    val circleOverlay = MKCircle.circleWithCenterCoordinate(
                        CLLocationCoordinate2DMake(coordinates.latitude, coordinates.longitude),
                        radius = it.circleMap.radius
                    )
                    mapView.addOverlay(circleOverlay)
                    //mapView.setVisibleMapRect(circleOverlay.boundingMapRect, edgePadding, true)
                }
            }

            mapView.removeGestureRecognizer(tapGestureRecognizer)
            mapView.addGestureRecognizer(tapGestureRecognizer)
            centerMapOnCoordinate(mapView, coordinates, zoom)
        },
        properties = UIKitInteropProperties(
            isNativeAccessibilityEnabled = true,
            interactionMode = UIKitInteropInteractionMode.NonCooperative
        )
    )
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Composable
fun tapGestureRecognizer(onMapClick: (Coordinates) -> Unit): UITapGestureRecognizer {
    val tapHandler = object : NSObject() {
        @ObjCAction
        fun handleTap(gestureRecognizer: UITapGestureRecognizer) {
            println("Se llamo el handleTap")
            val touchPoint = gestureRecognizer.locationInView(mapView)
            val coordinate =
                mapView.convertPoint(point = touchPoint, toCoordinateFromView = mapView)
            coordinate.useContents {
                println("Latitud: $latitude, Longitud: $longitude")
                onMapClick(Coordinates(latitude, longitude))
            }
        }
    }

    return UITapGestureRecognizer().apply {
        val name = tapHandler::handleTap.name + ":"
        println("Name: $name")
        val actionSelector = sel_registerName(name)
        addTarget(tapHandler, actionSelector)
    }
}

@Composable
fun rememberMapDelegate(): MKMapViewDelegateProtocol {
    return remember {
        object : NSObject(), MKMapViewDelegateProtocol {
            @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
            @ObjCSignatureOverride
            override fun mapView(
                mapView: MKMapView,
                rendererForOverlay: MKOverlayProtocol
            ): MKOverlayRenderer {
                return if (rendererForOverlay is MKCircle) {
                    val circleRenderer = MKCircleRenderer(overlay = rendererForOverlay)
                    circleRenderer.fillColor = UIColor.redColor().colorWithAlphaComponent(0.3)
                    circleRenderer.strokeColor = UIColor.redColor()
                    circleRenderer.lineWidth = 2.0
                    circleRenderer
                } else {
                    MKOverlayRenderer(rendererForOverlay)
                }
            }

            @Suppress("CONFLICTING_OVERLOADS", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
            @ObjCSignatureOverride
            override fun mapView(
                mapView: MKMapView,
                viewForAnnotation: MKAnnotationProtocol
            ): MKAnnotationView {
                val identifier = "customAnnotation"
                val annotationView = mapView.dequeueReusableAnnotationViewWithIdentifier(identifier)
                    ?: MKPinAnnotationView(
                        annotation = viewForAnnotation,
                        reuseIdentifier = identifier
                    ).apply {
                        canShowCallout = true
                        rightCalloutAccessoryView =
                            UIButton.buttonWithType(UIButtonTypeDetailDisclosure)
                    }

                if (viewForAnnotation is CustomPointAnnotation) {
                    annotationView.setTintColor(viewForAnnotation.geoPosition.markerMap.iconColor.toUIColor())
                }

                annotationView.annotation = viewForAnnotation
                return annotationView
            }

            @ObjCSignatureOverride
            override fun mapView(
                mapView: MKMapView,
                annotationView: MKAnnotationView,
                calloutAccessoryControlTapped: UIControl
            ) {
                val annotation = annotationView.annotation
                if (annotation is CustomPointAnnotation) {
                    annotation.geoPosition.markerMap.onInfoWindowClick.invoke(annotation.geoPosition)
                }
            }

            @ObjCSignatureOverride
            override fun mapView(
                mapView: MKMapView,
                didSelectAnnotationView: MKAnnotationView
            ) {
                val annotation = didSelectAnnotationView.annotation
                if (annotation is CustomPointAnnotation) {
                    annotation.geoPosition.markerMap.onClick.invoke(annotation.geoPosition)
                }
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
fun centerMapOnCoordinate(mapView: MKMapView, coordinates: Coordinates, zoom: Float) {
    MainScope().launch {
        val bounds = mapView.visibleMapRect.useContents { size }
        println("Width: ${bounds.width}, Height: ${bounds.height}")
        var shortestSideInPixels = minOf(bounds.width, bounds.height)
        if (shortestSideInPixels == 0.0) {
            shortestSideInPixels = 500.0
        }
        val radius = calculateRadiusForZoomLevel(shortestSideInPixels, zoom)
        val region = MKCoordinateRegionMakeWithDistance(
            CLLocationCoordinate2DMake(coordinates.latitude, coordinates.longitude),
            radius,
            radius
        )
        mapView.setRegion(region, animated = true)
    }
}

private fun calculateRadiusForZoomLevel(shortestSideInPixels: Double, zoomLevel: Float): Double {
    val maxMetersPerPixel = 156543.03392
    val metersPerPixel = maxMetersPerPixel / (1 shl zoomLevel.toInt())
    return metersPerPixel * shortestSideInPixels / 2
}

@OptIn(ExperimentalForeignApi::class)
actual suspend fun getLocation(updateLocation: Boolean): Coordinates =
    suspendCancellableCoroutine { cont ->
        val locationManager = CLLocationManager()
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val location = (didUpdateLocations.firstOrNull() as? CLLocation)
                if (cont.isActive) {
                    if (location != null) {
                        location.coordinate().useContents {
                            cont.resume(Coordinates(latitude, longitude))
                        }
                    } else {
                        cont.resumeWithException(Exception("Unable to get location"))
                    }
                    manager.stopUpdatingLocation()
                }
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                if (cont.isActive) {
                    cont.resumeWithException(Exception("Location error: ${didFailWithError.localizedDescription}"))
                    manager.stopUpdatingLocation()
                }
            }

            override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
                when (CLLocationManager.authorizationStatus()) {
                    kCLAuthorizationStatusDenied, kCLAuthorizationStatusRestricted -> {
                        if (cont.isActive) {
                            cont.resumeWithException(Exception("Location permissions denied or restricted"))
                        }
                    }

                    kCLAuthorizationStatusAuthorizedWhenInUse, kCLAuthorizationStatusAuthorizedAlways -> {
                        manager.startUpdatingLocation()
                    }

                    else -> {
                    }
                }
            }
        }

        locationManager.delegate = delegate
        locationManager.requestWhenInUseAuthorization()

        when (CLLocationManager.authorizationStatus()) {
            kCLAuthorizationStatusAuthorizedWhenInUse, kCLAuthorizationStatusAuthorizedAlways -> {
                locationManager.startUpdatingLocation()
            }

            else -> {
            }
        }

        cont.invokeOnCancellation {
            locationManager.stopUpdatingLocation()
        }
    }

//TODO This should be in base
fun Color.toUIColor(): UIColor {
    return UIColor(
        red = this.red.toDouble(),
        green = this.green.toDouble(),
        blue = this.blue.toDouble(),
        alpha = this.alpha.toDouble()
    )
}