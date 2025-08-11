# KMPMaps - Multiplatform Maps Library

KMPMaps is a Kotlin Multiplatform library that provides unified map functionality across Android and iOS platforms, using Google Maps on Android and MapKit on iOS.

## Features

- Cross-platform map component with native performance
- Marker management with custom icons and clustering
- Location services integration
- Polyline and polygon drawing
- Custom map styles and themes
- Gesture handling and camera controls
- Real-time location tracking

## Installation

### 1. Add Dependencies

Update your `libs.versions.toml` and `build.gradle.kts` files:

#### `libs.versions.toml`

```toml
[versions]
sweetmesoft = "{version-sweetmesoft}"

[libraries]
sweetmesoft-kmpmaps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }
```

#### `build.gradle.kts`

```kotlin
implementation(libs.sweetmesoft.kmpmaps)
```

### 2. Platform Configuration

#### Android Setup

Add Google Maps API key to your `android/src/main/res/values/strings.xml`:

```xml
<resources>
    <string name="google_maps_key">YOUR_API_KEY_HERE</string>
</resources>
```

Add the following to your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<application>
    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="@string/google_maps_key" />
</application>
```

#### iOS Setup

Add location permissions to your `Info.plist`:

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>This app needs location access to show maps</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>This app needs location access to show maps</string>
```

## Basic Usage

### Simple Map

```kotlin
@Composable
fun MapScreen() {
    var mapState by remember { mutableStateOf(MapState()) }
    
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        mapState = mapState,
        onMapStateChange = { mapState = it }
    )
}
```

### Map with Markers

```kotlin
@Composable
fun MapWithMarkers() {
    val markers = remember {
        listOf(
            MapMarker(
                id = "marker1",
                position = LatLng(37.7749, -122.4194),
                title = "San Francisco",
                snippet = "Golden Gate City"
            ),
            MapMarker(
                id = "marker2",
                position = LatLng(34.0522, -118.2437),
                title = "Los Angeles",
                snippet = "City of Angels"
            )
        )
    }
    
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        markers = markers,
        onMarkerClick = { marker ->
            println("Clicked marker: ${marker.title}")
        }
    )
}
```

### Location Tracking

```kotlin
@Composable
fun LocationTrackingMap() {
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    val locationManager = remember { LocationManager() }
    
    LaunchedEffect(Unit) {
        locationManager.startLocationUpdates { location ->
            currentLocation = LatLng(location.latitude, location.longitude)
        }
    }
    
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        showUserLocation = true,
        centerOnUserLocation = true,
        onLocationUpdate = { location ->
            currentLocation = location
        }
    )
}
```

### Custom Map Styles

```kotlin
@Composable
fun StyledMap() {
    val mapStyle = MapStyle(
        isDarkMode = true,
        showTraffic = false,
        showBuildings = true,
        customStyleJson = "your_custom_style.json"
    )
    
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        mapStyle = mapStyle
    )
}
```

## Advanced Features

### Drawing Polylines

```kotlin
val polyline = MapPolyline(
    points = listOf(
        LatLng(37.7749, -122.4194),
        LatLng(37.7849, -122.4094),
        LatLng(37.7949, -122.3994)
    ),
    color = Color.Blue,
    width = 5f
)

MapComponent(
    modifier = Modifier.fillMaxSize(),
    polylines = listOf(polyline)
)
```

### Marker Clustering

```kotlin
MapComponent(
    modifier = Modifier.fillMaxSize(),
    markers = largeMarkerList,
    enableClustering = true,
    clusteringOptions = ClusteringOptions(
        minClusterSize = 3,
        maxZoomLevel = 15
    )
)
```

## API Reference

For complete API documentation, visit the [KMPMaps Documentation](../docs/kmpmaps.md).

## Requirements

- Kotlin Multiplatform 1.9.0+
- Android API 28+
- iOS 12.0+
- Compose Multiplatform 1.5.0+

## License

This library is part of the SweetMeSoft KMP Library suite. See the main project LICENSE file for details.