# 🧪 KMPTestApp Documentation

KMPTestApp es la aplicación de prueba que demuestra el uso de todos los módulos de la librería SweetMeSoft KMP. Sirve como ejemplo de implementación y guía de mejores prácticas.

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Configuración](#configuración)
- [Pantallas de Ejemplo](#pantallas-de-ejemplo)
- [Navegación](#navegación)
- [Temas y Estilos](#temas-y-estilos)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Cómo Ejecutar](#cómo-ejecutar)

## 🎯 Descripción General

KMPTestApp es una aplicación completa que demuestra:

- ✅ Uso de todos los componentes de `KMPControls`
- ✅ Implementación de mapas con `KMPMaps`
- ✅ Utilización de controles avanzados de `Library`
- ✅ Navegación con Voyager
- ✅ Manejo de estados y ViewModels
- ✅ Temas personalizados
- ✅ Mejores prácticas de arquitectura

## 🏗️ Estructura del Proyecto

```
kmptestapp/
├── src/
│   ├── androidMain/
│   │   └── kotlin/
│   │       └── com/sweetmesoft/kmptestapp/
│   │           └── MainActivity.kt
│   ├── commonMain/
│   │   └── kotlin/
│   │       └── com/sweetmesoft/kmptestapp/
│   │           ├── App.kt                    # Punto de entrada principal
│   │           ├── PhotoProfileRequest.kt    # Modelo de datos
│   │           ├── screens/
│   │           │   ├── about/
│   │           │   │   └── AboutScreen.kt    # Pantalla Acerca de
│   │           │   ├── main/
│   │           │   │   ├── MainScreen.kt     # Pantalla principal
│   │           │   │   └── MainViewModel.kt  # ViewModel principal
│   │           │   └── splash/
│   │           │       └── SplashScreen.kt   # Pantalla de splash
│   │           └── theme/
│   │               ├── Color.kt              # Colores del tema
│   │               └── Theme.kt              # Configuración del tema
│   └── iosMain/
│       └── kotlin/
│           └── com/sweetmesoft/kmptestapp/
│               └── MainViewController.kt
└── build.gradle.kts
```

## ⚙️ Configuración

### Dependencias Principales

```kotlin
commonMain.dependencies {
    implementation(projects.library)
    implementation(projects.kmpcontrols)
    implementation(projects.kmpmaps)
    
    // Navegación
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.bottom.sheet.navigator)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.voyager.transitions)
    
    // UI y Compose
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(compose.ui)
    implementation(compose.components.resources)
    implementation(compose.components.uiToolingPreview)
    
    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
    
    // Utilidades
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
```

### Configuración Android

```kotlin
android {
    namespace = "com.sweetmesoft.kmptestapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    defaultConfig {
        applicationId = "com.sweetmesoft.kmptestapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
}
```

## 📱 Pantallas de Ejemplo

### App.kt - Punto de Entrada

```kotlin
@Composable
fun App() {
    AppTheme {
        Navigator(screen = SplashScreen()) { navigator ->
            // Configurar el navegador global
            BaseViewModel.navigator = navigator
            
            // Aplicar transiciones
            SlideTransition(navigator)
        }
    }
}
```

**Características:**
- Configuración del navegador global
- Aplicación de tema personalizado
- Transiciones entre pantallas
- Punto de entrada único para todas las plataformas

### SplashScreen.kt - Pantalla de Bienvenida

```kotlin
class SplashScreen : BaseScreen() {
    @Composable
    override fun ScreenContent() {
        var isLoading by remember { mutableStateOf(true) }
        
        LaunchedEffect(Unit) {
            delay(2000) // Simular carga
            isLoading = false
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Logo de la aplicación
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = Color.White
                )
                
                Text(
                    text = "KMP Test App",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Demostrando SweetMeSoft KMP Library",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
                
                if (isLoading) {
                    Spacer(modifier = Modifier.height(32.dp))
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                } else {
                    LaunchedEffect(Unit) {
                        navigator?.replace(MainScreen())
                    }
                }
            }
        }
    }
}
```

**Características:**
- Diseño atractivo con gradientes
- Animación de carga
- Transición automática a la pantalla principal
- Uso de BaseScreen para funcionalidades comunes

### MainScreen.kt - Pantalla Principal

```kotlin
class MainScreen : BaseBottomBarScreen() {
    @Composable
    override fun ScreenContent() {
        val viewModel = remember { MainViewModel() }
        val uiState by viewModel.uiState.collectAsState()
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                WelcomeCard()
            }
            
            item {
                ComponentsSection(viewModel)
            }
            
            item {
                ControlsSection(viewModel)
            }
            
            item {
                MapsSection(viewModel)
            }
            
            item {
                UtilitiesSection(viewModel)
            }
        }
        
        // Manejo de alertas y popups
        PopupHandler.currentPopup?.let { popup ->
            when (popup) {
                is PopupState.Alert -> {
                    AlertConfirm(
                        title = popup.title,
                        message = popup.message,
                        onConfirm = popup.onConfirm,
                        onCancel = popup.onCancel
                    )
                }
                is PopupState.Progress -> {
                    AlertProgress(
                        title = popup.title,
                        message = popup.message,
                        progress = popup.progress
                    )
                }
            }
        }
    }
    
    @Composable
    override fun BottomBarContent() {
        NavigationBar {
            NavigationBarItem(
                selected = true,
                onClick = { },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text("Inicio") }
            )
            NavigationBarItem(
                selected = false,
                onClick = { navigator?.push(AboutScreen()) },
                icon = { Icon(Icons.Default.Info, contentDescription = null) },
                label = { Text("Acerca de") }
            )
        }
    }
}
```

**Características:**
- Uso de BaseBottomBarScreen para navegación inferior
- Integración con ViewModel
- Secciones organizadas por funcionalidad
- Manejo de popups y alertas
- Navegación entre pantallas

### MainViewModel.kt - Lógica de Negocio

```kotlin
class MainViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    fun showDatePicker() {
        // Implementación del selector de fecha
    }
    
    fun showTimePicker() {
        // Implementación del selector de tiempo
    }
    
    fun showMapExample() {
        // Navegación a ejemplo de mapas
    }
    
    fun testNetworkCall() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val result = NetworkUtils.get("https://api.example.com/test")
                PopupHandler.showAlert(
                    title = "Éxito",
                    message = "Respuesta: $result"
                )
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }
    
    fun showImagePicker() {
        // Implementación del selector de imágenes
    }
}

data class MainUiState(
    val selectedDate: LocalDate? = null,
    val selectedTime: LocalTime? = null,
    val profileImage: String? = null,
    val searchQuery: String = "",
    val selectedCountry: String? = null
)
```

**Características:**
- Hereda de BaseViewModel para funcionalidades comunes
- Manejo de estados con StateFlow
- Operaciones asíncronas con corrutinas
- Integración con utilidades de red
- Manejo de errores centralizado

### AboutScreen.kt - Información de la App

```kotlin
class AboutScreen : BaseScreen() {
    @Composable
    override fun ScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header con botón de regreso
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navigator?.pop() }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Acerca de",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Información de la aplicación
            AboutContent(
                appName = "KMP Test App",
                version = "1.0.0",
                description = "Aplicación de demostración para SweetMeSoft KMP Library. " +
                        "Muestra el uso de todos los componentes y funcionalidades " +
                        "disponibles en la librería multiplataforma.",
                developers = listOf(
                    Developer(
                        name = "SweetMeSoft Team",
                        role = "Desarrollo",
                        email = "team@sweetmesoft.com"
                    )
                ),
                links = listOf(
                    AppLink(
                        title = "GitHub Repository",
                        url = "https://github.com/erickvelasco11/KmpLibrary",
                        icon = Icons.Default.Code
                    ),
                    AppLink(
                        title = "Documentación",
                        url = "https://erickvelasco11.github.io/KmpLibrary",
                        icon = Icons.Default.MenuBook
                    )
                )
            )
        }
    }
}
```

**Características:**
- Uso del componente AboutContent de la librería
- Navegación de regreso
- Información estructurada de la aplicación
- Enlaces a recursos externos

## 🎨 Temas y Estilos

### Color.kt - Paleta de Colores

```kotlin
val md_theme_light_primary = Color(0xFF6750A4)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFEADDFF)
val md_theme_light_onPrimaryContainer = Color(0xFF21005D)

val md_theme_dark_primary = Color(0xFFD0BCFF)
val md_theme_dark_onPrimary = Color(0xFF381E72)
val md_theme_dark_primaryContainer = Color(0xFF4F378B)
val md_theme_dark_onPrimaryContainer = Color(0xFFEADDFF)

// Colores personalizados
val SweetMeSoftBlue = Color(0xFF2196F3)
val SweetMeSoftGreen = Color(0xFF4CAF50)
val SweetMeSoftOrange = Color(0xFFFF9800)
```

### Theme.kt - Configuración del Tema

```kotlin
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    // ... más colores
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    // ... más colores
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
```

**Características:**
- Soporte para tema claro y oscuro
- Colores dinámicos en Android 12+
- Paleta de colores personalizada
- Tipografía Material Design 3

## 💡 Ejemplos de Uso

### Ejemplo 1: Sección de Componentes

```kotlin
@Composable
fun ComponentsSection(viewModel: MainViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Componentes KMPControls",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Selector de fecha
            ClickableOutlinedTextField(
                value = viewModel.uiState.value.selectedDate?.toString() ?: "",
                label = "Seleccionar Fecha",
                onClick = { viewModel.showDatePicker() },
                modifier = Modifier.fillMaxWidth()
            )
            
            // Selector de tiempo
            ClickableOutlinedTextField(
                value = viewModel.uiState.value.selectedTime?.toString() ?: "",
                label = "Seleccionar Hora",
                onClick = { viewModel.showTimePicker() },
                modifier = Modifier.fillMaxWidth()
            )
            
            // Control de contraseña
            var password by remember { mutableStateOf("") }
            PasswordControl(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña de Ejemplo",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
```

### Ejemplo 2: Sección de Mapas

```kotlin
@Composable
fun MapsSection(viewModel: MainViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Componentes KMPMaps",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Mapas multiplataforma con Google Maps (Android) y MapKit (iOS)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Button(
                onClick = { viewModel.showMapExample() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Map, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver Ejemplo de Mapa")
            }
        }
    }
}
```

### Ejemplo 3: Sección de Utilidades

```kotlin
@Composable
fun UtilitiesSection(viewModel: MainViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Utilidades Library",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Prueba de red
            Button(
                onClick = { viewModel.testNetworkCall() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CloudDownload, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Probar Llamada de Red")
            }
            
            // Selector de imágenes
            Button(
                onClick = { viewModel.showImagePicker() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Seleccionar Imagen")
            }
            
            // Ejemplo de validación
            var email by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email de Prueba") },
                isError = email.isNotBlank() && !StringUtils.isValidEmail(email),
                supportingText = {
                    if (email.isNotBlank() && !StringUtils.isValidEmail(email)) {
                        Text("Email inválido")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
```

## 🚀 Cómo Ejecutar

### Requisitos Previos

1. **Android Studio** (última versión estable)
2. **Xcode** (para desarrollo iOS)
3. **JDK 17** o superior
4. **Kotlin Multiplatform Mobile plugin**

### Pasos para Ejecutar

#### Android

```bash
# Clonar el repositorio
git clone https://github.com/erickvelasco11/KmpLibrary.git
cd KmpLibrary

# Ejecutar en Android
./gradlew :kmptestapp:installDebug

# O desde Android Studio
# 1. Abrir el proyecto
# 2. Seleccionar 'kmptestapp' como módulo
# 3. Ejecutar en dispositivo/emulador Android
```

#### iOS

```bash
# Generar proyecto Xcode
./gradlew :kmptestapp:embedAndSignAppleFrameworkForXcode

# Abrir en Xcode
open iosApp/iosApp.xcodeproj

# O desde Android Studio con KMP plugin
# 1. Seleccionar configuración iOS
# 2. Ejecutar en simulador/dispositivo iOS
```

#### Desktop (Experimental)

```bash
# Ejecutar versión desktop
./gradlew :kmptestapp:run
```

### Configuración de API Keys

Para usar las funcionalidades de mapas, configura las API keys:

#### Android
```xml
<!-- android/src/main/res/values/strings.xml -->
<resources>
    <string name="google_maps_key">TU_API_KEY_AQUI</string>
</resources>
```

#### iOS
```xml
<!-- iosApp/iosApp/Info.plist -->
<key>NSLocationWhenInUseUsageDescription</key>
<string>Esta app necesita acceso a la ubicación para mostrar mapas</string>
```

## 📚 Recursos de Aprendizaje

### Patrones Implementados

1. **MVVM (Model-View-ViewModel)**
   - Separación clara de responsabilidades
   - Estados reactivos con StateFlow
   - Manejo de ciclo de vida

2. **Repository Pattern**
   - Abstracción de fuentes de datos
   - Manejo de caché y red

3. **Navigation Pattern**
   - Navegación declarativa con Voyager
   - Manejo de back stack
   - Transiciones personalizadas

4. **Dependency Injection**
   - Inyección manual para simplicidad
   - Fácil testing y mantenimiento

### Mejores Prácticas Demostradas

- ✅ Manejo de estados con Compose
- ✅ Navegación multiplataforma
- ✅ Temas adaptativos
- ✅ Manejo de errores centralizado
- ✅ Operaciones asíncronas
- ✅ Validación de formularios
- ✅ Componentes reutilizables
- ✅ Arquitectura escalable

---

**¿Necesitas ayuda?**
- [Documentación Completa](../README.md)
- [Reportar Issues](https://github.com/erickvelasco11/KmpLibrary/issues)
- [Contribuir](../CONTRIBUTING.md)