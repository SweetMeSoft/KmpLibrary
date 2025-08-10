# 📗 KMPMaps Documentation

KMPMaps es el módulo especializado de la librería SweetMeSoft KMP que proporciona componentes de mapas multiplataforma con integración nativa para Android (Google Maps) e iOS (MapKit).

## 📋 Tabla de Contenidos

- [Instalación](#instalación)
- [Configuración](#configuración)
- [Componentes Principales](#componentes-principales)
- [Controles de Mapa](#controles-de-mapa)
- [Gestión de Ubicación](#gestión-de-ubicación)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Personalización](#personalización)
- [Solución de Problemas](#solución-de-problemas)

## 🚀 Instalación

```kotlin
commonMain.dependencies {
    implementation("com.sweetmesoft.kmpmaps:kmpmaps:1.6.6")
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:1.6.6")
    
    // Dependencias requeridas
    implementation("dev.icerock.moko:permissions:0.19.1")
    implementation("dev.icerock.moko:permissions-compose:0.19.1")
    implementation("dev.icerock.moko:permissions-location:0.19.1")
    implementation("com.google.maps.android:maps-compose:6.7.1")
}

androidMain.dependencies {
    implementation("com.google.android.gms:play-services-location:21.3.0")
}
```

## ⚙️ Configuración

### Android

1. **Agregar API Key de Google Maps**

En `android/src/main/AndroidManifest.xml`:
```xml
<application>
    <meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="YOUR_GOOGLE_MAPS_API_KEY" />
</application>
```

2. **Permisos de Ubicación**
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

### iOS

1. **Configurar Info.plist**
```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>Esta app necesita acceso a la ubicación para mostrar mapas</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>Esta app necesita acceso a la ubicación para funciones de mapas</string>
```

## 🗺️ Componentes Principales

### MapComponent
Componente principal de mapa multiplataforma que se adapta automáticamente a la plataforma.

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
Clase de datos para representar coordenadas geográficas.

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
Posición geográfica con información adicional.

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
Marcador personalizable para el mapa.

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
Circulo overlay para el mapa.

```kotlin
data class CircleMap(
    val id: String,
    val center: Coordinates,
    val radius: Double, // en metros
    val strokeColor: Color = Color.Blue,
    val fillColor: Color = Color.Blue.copy(alpha = 0.3f),
    val strokeWidth: Float = 2f,
    val isVisible: Boolean = true
)
```

## 📍 Gestión de Ubicación

### Obtener Ubicación Actual

```kotlin
@Composable
fun LocationExample() {
    var currentLocation by remember { mutableStateOf<GeoPosition?>(null) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    
    // Solicitar permisos
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
                        title = "Mi Ubicación",
                        color = Color.Blue
                    )
                )
            )
        }
    }
}

suspend fun getCurrentLocation(): GeoPosition? {
    return try {
        // Implementación específica de plataforma
        // Retorna la ubicación actual del usuario
        null // Placeholder
    } catch (e: Exception) {
        null
    }
}

suspend fun requestLocationPermission(): Boolean {
    // Implementación de solicitud de permisos
    return true // Placeholder
}
```

## 📝 Ejemplos de Uso

### Ejemplo 1: Mapa Básico con Marcadores

```kotlin
@Composable
fun BasicMapExample() {
    val markers = remember {
        listOf(
            MarkerMap(
                id = "marker1",
                position = Coordinates(40.7128, -74.0060),
                title = "Nueva York",
                description = "La Gran Manzana",
                color = Color.Red
            ),
            MarkerMap(
                id = "marker2",
                position = Coordinates(34.0522, -118.2437),
                title = "Los Ángeles",
                description = "Ciudad de los Ángeles",
                color = Color.Blue
            )
        )
    }
    
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        initialPosition = GeoPosition(39.8283, -98.5795), // Centro de EE.UU.
        zoom = 4f,
        markers = markers,
        onMarkerClick = { marker ->
            println("Clicked marker: ${marker.title}")
        }
    )
}
```

### Ejemplo 2: Mapa con Círculos y Áreas

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

### Ejemplo 3: Mapa Interactivo con Gestión de Estado

```kotlin
@Composable
fun InteractiveMapExample() {
    var markers by remember { mutableStateOf(emptyList<MarkerMap>()) }
    var selectedMarker by remember { mutableStateOf<MarkerMap?>(null) }
    
    Column {
        // Información del marcador seleccionado
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
        
        // Mapa
        MapComponent(
            modifier = Modifier.fillMaxSize(),
            initialPosition = GeoPosition(40.7128, -74.0060),
            markers = markers,
            onMapClick = { coordinates ->
                // Agregar nuevo marcador al hacer clic
                val newMarker = MarkerMap(
                    id = "marker_${System.currentTimeMillis()}",
                    position = coordinates,
                    title = "Marcador ${markers.size + 1}",
                    description = "Agregado en ${coordinates.latitude}, ${coordinates.longitude}"
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

### Ejemplo 4: Seguimiento de Ubicación en Tiempo Real

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
                delay(5000) // Actualizar cada 5 segundos
            }
        }
    }
    
    Column {
        // Controles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { isTracking = !isTracking }
            ) {
                Text(if (isTracking) "Detener" else "Iniciar Seguimiento")
            }
            
            Button(
                onClick = { locationHistory = emptyList() }
            ) {
                Text("Limpiar Historial")
            }
        }
        
        // Mapa
        currentLocation?.let { location ->
            MapComponent(
                modifier = Modifier.fillMaxSize(),
                initialPosition = location,
                showUserLocation = true,
                markers = locationHistory.mapIndexed { index, pos ->
                    MarkerMap(
                        id = "history_$index",
                        position = pos.toCoordinates(),
                        title = "Punto $index",
                        color = if (index == locationHistory.lastIndex) Color.Red else Color.Gray
                    )
                }
            )
        }
    }
}
```

## 🎨 Personalización

### Tipos de Mapa

```kotlin
enum class MapType {
    NORMAL,
    SATELLITE,
    TERRAIN,
    HYBRID
}

// Uso
MapComponent(
    mapType = MapType.SATELLITE
)
```

### Marcadores Personalizados

```kotlin
@Composable
fun CustomMarkerExample() {
    val customIcon = remember {
        // Crear icono personalizado
        createCustomMarkerIcon()
    }
    
    val marker = MarkerMap(
        id = "custom",
        position = Coordinates(40.7128, -74.0060),
        title = "Marcador Personalizado",
        icon = customIcon,
        isDraggable = true
    )
    
    MapComponent(
        markers = listOf(marker)
    )
}

fun createCustomMarkerIcon(): ImageBitmap {
    // Implementación específica de plataforma
    // para crear iconos personalizados
    return ImageBitmap(32, 32) // Placeholder
}
```

## 🔧 Configuración Avanzada

### Gestión de Permisos

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
                Text("Solicitar Permisos de Ubicación")
            }
        }
    }
}
```

### Optimización de Rendimiento

```kotlin
@Composable
fun OptimizedMapExample() {
    var visibleMarkers by remember { mutableStateOf(emptyList<MarkerMap>()) }
    val allMarkers = remember { generateLargeMarkerList() }
    
    MapComponent(
        markers = visibleMarkers,
        onCameraMove = { bounds ->
            // Filtrar marcadores visibles para mejor rendimiento
            visibleMarkers = allMarkers.filter { marker ->
                bounds.contains(marker.position)
            }
        }
    )
}
```

## 🐛 Solución de Problemas

### Problemas Comunes

1. **Mapa no se muestra en Android**
   ```kotlin
   // Verificar que la API Key esté configurada correctamente
   // y que los servicios de Google Play estén disponibles
   ```

2. **Permisos de ubicación denegados**
   ```kotlin
   // Implementar manejo adecuado de permisos
   // Mostrar diálogos explicativos al usuario
   ```

3. **Rendimiento lento con muchos marcadores**
   ```kotlin
   // Implementar clustering de marcadores
   // Filtrar marcadores por región visible
   ```

4. **Problemas de compilación en iOS**
   ```kotlin
   // Verificar que los permisos estén configurados en Info.plist
   // Asegurar compatibilidad con la versión de iOS
   ```

### Debugging

```kotlin
// Habilitar logs de debug
MapComponent(
    debugMode = true, // Solo en desarrollo
    onDebugInfo = { info ->
        println("Map Debug: $info")
    }
)
```

## 📚 Recursos Adicionales

- [Google Maps API Documentation](https://developers.google.com/maps/documentation)
- [Apple MapKit Documentation](https://developer.apple.com/documentation/mapkit)
- [Moko Permissions](https://github.com/icerockdev/moko-permissions)
- [Ejemplos Completos](../kmptestapp/)
- [Reportar Issues](https://github.com/erickvelasco11/KmpLibrary/issues)

---

**Próximas Características:**
- Clustering de marcadores
- Rutas y direcciones
- Mapas offline
- Más tipos de overlays
- Integración con servicios de geocodificación