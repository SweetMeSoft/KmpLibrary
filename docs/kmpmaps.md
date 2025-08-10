# 📗 KMPMaps Documentation

KMPMaps is the specialized module of the SweetMeSoft KMP library that provides multiplatform map components with native integration for Android (Google Maps) and iOS (MapKit).

## 📋 Table of Contents

- [Installation](#-installation)
- [Configuration](#-configuration)
- [Main Components](#-main-components)
- [Map Controls](#-map-controls)
- [Location Management](#-location-management)
- [Usage Examples](#-usage-examples)
- [Customization](#-customization)
- [Troubleshooting](#-troubleshooting)

## 🚀 Installation

```kotlin
commonMain.dependencies {
    implementation("com.sweetmesoft.kmpmaps:kmpmaps:1.6.6")
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:1.6.6")
    
    // Required dependencies
    implementation("dev.icerock.moko:permissions:0.19.1")
    implementation("dev.icerock.moko:permissions-compose:0.19.1")
    implementation("dev.icerock.moko:permissions-location:0.19.1")
    implementation("com.google.maps.android:maps-compose:6.7.1")
}

androidMain.dependencies {
    implementation("com.google.android.gms:play-services-location:21.3.0")
}
```

## ⚙️ Configuration

### Android

#### 1. Google Maps API Key

Add your API key in `local.properties`:
```properties
GOOGLE_MAPS_API_KEY=your_api_key_here
```

In `build.gradle.kts` (app module):
```kotlin
android {
    defaultConfig {
        val googleMapsApiKey = project.findProperty("GOOGLE_MAPS_API_KEY") as String? ?: ""
        buildConfigField("String", "GOOGLE_MAPS_API_KEY", "\"$googleMapsApiKey\"")
        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = googleMapsApiKey
    }
}
```

#### 2. Permissions in AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />

<application>
    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="${GOOGLE_MAPS_API_KEY}" />
</application>
```

### iOS

1. **Configure Info.plist**
```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>This application needs location access to display maps</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>This application needs location access to display maps</string>
```

## 🗺️ Main Components

### MapComponent
Main multiplatform map component that automatically adapts to the platform.

```kotlin
@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    initialPosition: GeoPosition = GeoPosition(0.0, 0.0),
    zoom: Float = 15f,
    markers: List<MarkerMap> = emptyList(),
    circles: List<CircleMap> = emptyList(),
    onMapClick: (Coordinates) -> Unit = {},
    onMarkerClick: (MarkerMap) -> Unit = {},
    showUserLocation: Boolean = true,
    mapType: MapType = MapType.NORMAL
)
```

**Ejemplo básico:**
```kotlin
MapComponent(
    modifier = Modifier.fillMaxSize(),
    initialPosition = GeoPosition(40.7128, -74.0060), // Nueva York
    zoom = 12f,
    showUserLocation = true,
    onMapClick = { coordinates ->
        println("Clicked at: ${coordinates.latitude}, ${coordinates.longitude}")
    }
)
```

## 🎯 Controles de Mapa

### Coordinates
Data class to represent geographic coordinates.

```kotlin
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) {
    fun distanceTo(other: Coordinates): Double
    fun bearingTo(other: Coordinates): Double
    fun isValid(): Boolean
}
```

### GeoPosition
Geographic position with additional information.

```kotlin
data class GeoPosition(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double = 0.0,
    val accuracy: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toCoordinates(): Coordinates
    fun distanceTo(other: GeoPosition): Double
}
```

### MarkerMap
Customizable marker for the map.

```kotlin
data class MarkerMap(
    val id: String,
    val position: Coordinates,
    val title: String = "",
    val description: String = "",
    val icon: ImageBitmap? = null,
    val color: Color = Color.Red,
    val isVisible: Boolean = true,
    val isDraggable: Boolean = false,
    val onClick: (MarkerMap) -> Unit = {}
)
```

### CircleMap
Circle overlay for the map.

```kotlin
data class CircleMap(
    val id: String,
    val center: Coordinates,
    val radius: Double, // in meters
    val strokeColor: Color = Color.Blue,
    val fillColor: Color = Color.Blue.copy(alpha = 0.3f),
    val strokeWidth: Float = 2f,
    val isVisible: Boolean = true
)
```

## 📍 Location Management

### Get Current Location

```kotlin
@Composable
fun LocationExample() {
    var currentLocation by remember { mutableStateOf<GeoPosition?>(null) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    
    // Request permissions
    LaunchedEffect(Unit) {
        hasLocationPermission = requestLocationPermission()
    }
    
    if (hasLocationPermission) {
        LaunchedEffect(Unit) {
            currentLocation = getCurrentLocation()
        }
        
        currentLocation?.let { location ->
            MapComponent(
                initialPosition = location,
                showUserLocation = true,
                markers = listOf(
                    MarkerMap(
                        id = "current",
                        position = location.toCoordinates(),
                        title = "My Location",
                        color = Color.Blue
                    )
                )
            )
        }
    }
}

suspend fun getCurrentLocation(): GeoPosition? {
    return try {
        // Platform-specific implementation
        // Returns the user's current location
        null // Placeholder
    } catch (e: Exception) {
        null
    }
}

suspend fun requestLocationPermission(): Boolean {
    // Permission request implementation
    return true // Placeholder
}
```

## 📝 Usage Examples

### Example 1: Basic Map with Markers

```kotlin
@Composable
fun BasicMapExample() {
    val markers = remember {
        listOf(
            MarkerMap(
                id = "marker1",
                position = Coordinates(40.7128, -74.0060),
                title = "New York",
                description = "The Big Apple",
                color = Color.Red
            ),
            MarkerMap(
                id = "marker2",
                position = Coordinates(34.0522, -118.2437),
                title = "Los Angeles",
                description = "City of Angels",
                color = Color.Blue
            )
        )
    }
    
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        initialPosition = GeoPosition(39.8283, -98.5795), // Center of USA
        zoom = 4f,
        markers = markers,
        onMarkerClick = { marker ->
            println("Clicked marker: ${marker.title}")
        }
    )
}
```

### Example 2: Map with Circles and Areas

```kotlin
@Composable
fun MapWithCirclesExample() {
    val circles = remember {
        listOf(
            CircleMap(
                id = "area1",
                center = Coordinates(40.7128, -74.0060),
                radius = 1000.0, // 1km
                strokeColor = Color.Red,
                fillColor = Color.Red.copy(alpha = 0.2f)
            ),
            CircleMap(
                id = "area2",
                center = Coordinates(40.7589, -73.9851),
                radius = 500.0, // 500m
                strokeColor = Color.Green,
                fillColor = Color.Green.copy(alpha = 0.2f)
            )
        )
    }
    
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        initialPosition = GeoPosition(40.7128, -74.0060),
        zoom = 12f,
        circles = circles
    )
}
```

### Example 3: Interactive Map with State Management

```kotlin
@Composable
fun InteractiveMapExample() {
    var markers by remember { mutableStateOf(emptyList<MarkerMap>()) }
    var selectedMarker by remember { mutableStateOf<MarkerMap?>(null) }
    
    Column {
        // Selected marker information
        selectedMarker?.let { marker ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = marker.title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = marker.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Lat: ${marker.position.latitude}, Lng: ${marker.position.longitude}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        
        // Map
        MapComponent(
            modifier = Modifier.fillMaxSize(),
            initialPosition = GeoPosition(40.7128, -74.0060),
            markers = markers,
            onMapClick = { coordinates ->
                // Add new marker on click
                val newMarker = MarkerMap(
                    id = "marker_${System.currentTimeMillis()}",
                    position = coordinates,
                    title = "Marker ${markers.size + 1}",
                    description = "Added at ${coordinates.latitude}, ${coordinates.longitude}"
                )
                markers = markers + newMarker
            },
            onMarkerClick = { marker ->
                selectedMarker = marker
            }
        )
    }
}
```

### Example 4: Real-time Location Tracking

```kotlin
@Composable
fun LocationTrackingExample() {
    var currentLocation by remember { mutableStateOf<GeoPosition?>(null) }
    var locationHistory by remember { mutableStateOf(emptyList<GeoPosition>()) }
    var isTracking by remember { mutableStateOf(false) }
    
    LaunchedEffect(isTracking) {
        if (isTracking) {
            while (isTracking) {
                getCurrentLocation()?.let { location ->
                    currentLocation = location
                    locationHistory = locationHistory + location
                }
                delay(5000) // Update every 5 seconds
            }
        }
    }
    
    Column {
        // Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { isTracking = !isTracking }
            ) {
                Text(if (isTracking) "Stop" else "Start Tracking")
            }
            
            Button(
                onClick = { locationHistory = emptyList() }
            ) {
                Text("Clear History")
            }
        }
        
        // Map
        currentLocation?.let { location ->
            MapComponent(
                modifier = Modifier.fillMaxSize(),
                initialPosition = location,
                showUserLocation = true,
                markers = locationHistory.mapIndexed { index, pos ->
                    MarkerMap(
                        id = "history_$index",
                        position = pos.toCoordinates(),
                        title = "Point $index",
                        color = if (index == locationHistory.lastIndex) Color.Red else Color.Gray
                    )
                }
            )
        }
    }
}
```

## 🎨 Customization

### Map Types

```kotlin
enum class MapType {
    NORMAL,
    SATELLITE,
    TERRAIN,
    HYBRID
}

// Usage
MapComponent(
    mapType = MapType.SATELLITE
)
```

### Custom Markers

```kotlin
@Composable
fun CustomMarkerExample() {
    val customIcon = remember {
        // Create custom icon
        createCustomMarkerIcon()
    }
    
    val marker = MarkerMap(
        id = "custom",
        position = Coordinates(40.7128, -74.0060),
        title = "Custom Marker",
        icon = customIcon,
        isDraggable = true
    )
    
    MapComponent(
        markers = listOf(marker)
    )
}

fun createCustomMarkerIcon(): ImageBitmap {
    // Platform-specific implementation
    // for creating custom icons
    return ImageBitmap(32, 32) // Placeholder
}
```

## 🔧 Advanced Configuration

### Permission Management

```kotlin
@Composable
fun PermissionAwareMap() {
    val permissionState = rememberPermissionState(
        permission = Permission.LOCATION
    )
    
    when {
        permissionState.hasPermission -> {
            MapComponent(
                showUserLocation = true
            )
        }
        permissionState.shouldShowRationale -> {
            PermissionRationaleDialog(
                onRequestPermission = {
                    permissionState.launchPermissionRequest()
                }
            )
        }
        else -> {
            Button(
                onClick = {
                    permissionState.launchPermissionRequest()
                }
            ) {
                Text("Request Location Permissions")
            }
        }
    }
}
```

### Performance Optimization

```kotlin
@Composable
fun OptimizedMapExample() {
    var visibleMarkers by remember { mutableStateOf(emptyList<MarkerMap>()) }
    val allMarkers = remember { generateLargeMarkerList() }
    
    MapComponent(
        markers = visibleMarkers,
        onCameraMove = { bounds ->
            // Filter visible markers for better performance
            visibleMarkers = allMarkers.filter { marker ->
                bounds.contains(marker.position)
            }
        }
    )
}
```

## 🐛 Troubleshooting

### Common Issues

1. **Map not showing on Android**
   ```kotlin
   // Verify that the API Key is configured correctly
   // and that Google Play Services are available
   ```

2. **Location permissions denied**
   ```kotlin
   // Implement proper permission handling
   // Show explanatory dialogs to the user
   ```

3. **Slow performance with many markers**
   ```kotlin
   // Implement marker clustering
   // Filter markers by visible region
   ```

4. **iOS compilation issues**
   ```kotlin
   // Verify that permissions are configured in Info.plist
   // Ensure compatibility with iOS version
   ```

### Debugging

```kotlin
// Enable debug logs
MapComponent(
    debugMode = true, // Development only
    onDebugInfo = { info ->
        println("Map Debug: $info")
    }
)
```

## 📚 Additional Resources

- [Google Maps API Documentation](https://developers.google.com/maps/documentation)
- [Apple MapKit Documentation](https://developer.apple.com/documentation/mapkit)
- [Moko Permissions](https://github.com/icerockdev/moko-permissions)
- [Complete Examples](../kmptestapp/)
- [Report Issues](https://github.com/erickvelasco11/KmpLibrary/issues)

---

**Upcoming Features:**
- Marker clustering
- Routes and directions
- Offline maps
- More overlay types
- Geocoding services integration