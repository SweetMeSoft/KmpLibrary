# KMPMaps

KMPMaps is a Kotlin Multiplatform library that provides unified map functionality across Android and iOS platforms, leveraging native map implementations (Google Maps on Android and MapKit on iOS) with a simple, composable API.

## Features

- **Cross-platform Map Component**: A unified `MapComponent` for Android and iOS.
- **Markers**: Easily add markers with custom titles, snippets, and colors.
- **Routes (Polylines)**: Draw routes on the map with customizable colors and widths.
- **Shapes**: Draw circles on the map.
- **Location Services**: Integrated user location display and retrieval.
- **Map Controls**: Toggle zoom, scroll, rotation, traffic, buildings, and more.
- **Utilities**: Helper functions for distance calculation and zoom level estimation.

## Installation

Add the dependency to your project.

### Using Version Catalog

If you are using a version catalog (e.g., `libs.versions.toml`), add the following:

```toml
[versions]
sweetmesoft = "2.0.1"

[libraries]
sweetmesoft-kmpmaps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }
```

### Build Gradle

In your `build.gradle.kts` (commonMain source set):

```kotlin
implementation(libs.sweetmesoft.kmpmaps)
```

## Platform Configuration

### Android

1.  **API Key**: Add your Google Maps API key to your `local.properties` or directly in `AndroidManifest.xml` (though `local.properties` is safer, for this guide we show the manifest meta-data).

    In `AndroidManifest.xml`:
    ```xml
    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="YOUR_GOOGLE_MAPS_API_KEY" />
    ```

2.  **Permissions**: Add location permissions if you intend to use location features.
    ```xml
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    ```

### iOS

1.  **Permissions**: Add location usage descriptions to your `Info.plist` if you use location features.

    ```xml
    <key>NSLocationWhenInUseUsageDescription</key>
    <string>We need your location to show it on the map.</string>
    <key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
    <string>We need your location to show it on the map.</string>
    ```

## Usage

### Basic Map

Display a simple map centered at a specific location.

```kotlin
import com.sweetmesoft.kmpmaps.MapComponent
import com.sweetmesoft.kmpmaps.controls.Coordinates

@Composable
fun SimpleMap() {
    val center = Coordinates(37.7749, -122.4194) // San Francisco
    
    MapComponent(
        coordinates = center,
        zoom = 12f
    )
}
```

### Adding Markers

Markers are added via the `markers` parameter, which takes a list of `GeoPosition` objects. Each `GeoPosition` can have a `MarkerMap` configuration.

```kotlin
import com.sweetmesoft.kmpmaps.controls.GeoPosition
import com.sweetmesoft.kmpmaps.controls.MarkerMap
import androidx.compose.ui.graphics.Color

val markers = listOf(
    GeoPosition(
        coordinates = Coordinates(37.7749, -122.4194),
        markerMap = MarkerMap(
            isVisible = true,
            title = "San Francisco",
            snippet = "A beautiful city",
            iconColor = Color.Red,
            onClick = { position ->
                println("Clicked: ${position.markerMap.title}")
                true // Return true to consume the event
            }
        )
    )
)

MapComponent(
    coordinates = Coordinates(37.7749, -122.4194),
    markers = markers
)
```

### Drawing Routes (Polylines)

Draw lines on the map using `RouteMap`.

```kotlin
import com.sweetmesoft.kmpmaps.controls.RouteMap

val route = RouteMap(
    points = listOf(
        Coordinates(37.7749, -122.4194),
        Coordinates(34.0522, -118.2437) // LA
    ),
    color = Color.Blue,
    width = 5f
)

MapComponent(
    coordinates = Coordinates(36.0, -120.0),
    zoom = 6f,
    routes = listOf(route)
)
```

### Drawing Circles

Circles are also attached to a `GeoPosition` via the `CircleMap` property.

```kotlin
import com.sweetmesoft.kmpmaps.controls.CircleMap

val circlePosition = GeoPosition(
    coordinates = Coordinates(37.7749, -122.4194),
    circleMap = CircleMap(
        radius = 1000.0, // meters
        fillColor = Color.Blue.copy(alpha = 0.3f),
        strokeColor = Color.Blue,
        strokeWidth = 2f
    )
)

MapComponent(
    coordinates = Coordinates(37.7749, -122.4194),
    markers = listOf(circlePosition)
)
```

### User Location

Enable the user's location display on the map.

```kotlin
MapComponent(
    coordinates = center,
    locationEnabled = true, // Shows the user location dot
    // ...
)
```

To get the current location programmatically:

```kotlin
import com.sweetmesoft.kmpmaps.getLocation

LaunchedEffect(Unit) {
    try {
        val location = getLocation(updateLocation = true)
        println("User location: ${location.latitude}, ${location.longitude}")
    } catch (e: Exception) {
        println("Error getting location: ${e.message}")
    }
}
```

## API Reference

### MapComponent

| Parameter | Type | Description | Default |
| :--- | :--- | :--- | :--- |
| `coordinates` | `Coordinates` | Center coordinates of the map. | Required |
| `zoom` | `Float` | Initial zoom level. | `1f` |
| `zoomEnabled` | `Boolean` | Enable zoom gestures. | `true` |
| `scrollEnabled` | `Boolean` | Enable scroll gestures. | `true` |
| `rotateEnabled` | `Boolean` | Enable rotation gestures. | `false` |
| `locationEnabled` | `Boolean` | Show user location (requires permissions). | `false` |
| `markers` | `List<GeoPosition>` | List of positions to display markers or circles. | `emptyList()` |
| `routes` | `List<RouteMap>` | List of polylines to draw. | `emptyList()` |
| `showTraffic` | `Boolean` | Show traffic layer. | `false` |
| `showBuildings` | `Boolean` | Show 3D buildings. | `false` |
| `showCompass` | `Boolean` | Show compass. | `false` |
| `onMapClick` | `(Coordinates) -> Unit` | Callback for map click. | `{}` |
| `onMapLongClick` | `(Coordinates) -> Unit` | Callback for map long click. | `{}` |

### Data Classes

**Coordinates**
- `latitude`: `Double`
- `longitude`: `Double`

**GeoPosition**
- `coordinates`: `Coordinates`
- `address`: `String`
- `markerMap`: `MarkerMap`
- `circleMap`: `CircleMap`

**MarkerMap**
- `isVisible`: `Boolean`
- `title`: `String`
- `snippet`: `String`
- `iconColor`: `Color`
- `onClick`: `(GeoPosition) -> Boolean`
- `onInfoWindowClick`: `(GeoPosition) -> Unit`

**RouteMap**
- `points`: `List<Coordinates>`
- `color`: `Color`
- `width`: `Float`

**CircleMap**
- `radius`: `Double` (in meters)
- `fillColor`: `Color`
- `strokeColor`: `Color`
- `strokeWidth`: `Float`

## Utilities

- `getLocation(updateLocation: Boolean)`: Suspend function to get current user location.
- `calculateDistance(from: Coordinates, to: Coordinates)`: Returns distance in meters.
- `calculateZoomByRadius(radiusInMeters: Double)`: Returns appropriate zoom level for a given radius.
- `calculateZoomAndCenter(positions: List<Coordinates>)`: Returns a pair of `Float` (zoom) and `Coordinates` (center) to fit all positions.

## Requirements

- **Android**: Min SDK 28, Target SDK 36
- **iOS**: 12.0+
- **Kotlin**: 2.2.21+
- **Compose Multiplatform**: 1.9.0+

## License

This project is licensed under the MIT License.
