---
layout: default
title: KMPTestApp
nav_order: 5
---

# KMPTestApp Documentation

KMPTestApp is the test application that demonstrates the use of all modules of the SweetMeSoft KMP library. It serves as an implementation example and best practices guide.

## Table of Contents

- [General Description](#general-description)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Example Screens](#example-screens)
- [Navigation](#navigation)
- [Themes and Styles](#themes-and-styles)
- [Usage Examples](#usage-examples)
- [How to Run](#how-to-run)

## General Description

KMPTestApp is a complete application that demonstrates:

- ✅ Use of all `KMPControls` components
- ✅ Maps implementation with `KMPMaps`
- ✅ Utilization of advanced `Library` controls
- ✅ Navigation with Voyager
- ✅ State and ViewModels management
- ✅ Custom themes
- ✅ Architecture best practices

## Project Structure

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
│   │           ├── App.kt                    # Main entry point
│   │           ├── PhotoProfileRequest.kt    # Data model
│   │           ├── screens/
│   │           │   ├── about/
│   │           │   │   └── AboutScreen.kt    # About screen
│   │           │   ├── main/
│   │           │   │   ├── MainScreen.kt     # Main screen
│   │           │   │   └── MainViewModel.kt  # Main ViewModel
│   │           │   └── splash/
│   │           │       └── SplashScreen.kt   # Splash screen
│   │           └── theme/
│   │               ├── Color.kt              # Theme colors
│   │               └── Theme.kt              # Theme configuration
│   └── iosMain/
│       └── kotlin/
│           └── com/sweetmesoft/kmptestapp/
│               └── MainViewController.kt
└── build.gradle.kts
```

## Configuration

### Main Dependencies

```kotlin
commonMain.dependencies {
    implementation(projects.library)
    implementation(projects.kmpcontrols)
    implementation(projects.kmpmaps)
    
    // Navigation
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.bottom.sheet.navigator)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.voyager.transitions)
    
    // UI and Compose
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(compose.ui)
    implementation(compose.components.resources)
    implementation(compose.components.uiToolingPreview)
    
    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
    
    // Utilities
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
```

### Android Configuration

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

## Example Screens

### App.kt - Entry Point

```kotlin
@Composable
fun App() {
    AppTheme {
        Navigator(screen = SplashScreen()) { navigator ->
            // Configure global navigator
            BaseViewModel.navigator = navigator
            
            // Apply transitions
            SlideTransition(navigator)
        }
    }
}
```

**Features:**
- Global navigator configuration
- Custom theme application
- Screen transitions
- Single entry point for all platforms

### SplashScreen.kt - Welcome Screen

```kotlin
class SplashScreen : BaseScreen() {
    @Composable
    override fun ScreenContent() {
        var isLoading by remember { mutableStateOf(true) }
        
        LaunchedEffect(Unit) {
            delay(2000) // Simulate loading
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
                // Application logo
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
                    text = "Demonstrating SweetMeSoft KMP Library",
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

**Features:**
- Attractive design with gradients
- Loading animation
- Automatic transition to main screen
- Use of BaseScreen for common functionalities

### MainScreen.kt - Main Screen

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
        
        // Alert and popup handling
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
                label = { Text("Home") }
            )
            NavigationBarItem(
                selected = false,
                onClick = { navigator?.push(AboutScreen()) },
                icon = { Icon(Icons.Default.Info, contentDescription = null) },
                label = { Text("About") }
            )
        }
    }
}
```

**Features:**
- Use of BaseBottomBarScreen for bottom navigation
- ViewModel integration
- Sections organized by functionality
- Popup and alert handling
- Navigation between screens

### MainViewModel.kt - Business Logic

```kotlin
class MainViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    fun showDatePicker() {
        // Date picker implementation
    }
    
    fun showTimePicker() {
        // Time picker implementation
    }
    
    fun showMapExample() {
        // Navigation to maps example
    }
    
    fun testNetworkCall() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val result = NetworkUtils.get("https://api.example.com/test")
                PopupHandler.showAlert(
                    title = "Success",
                    message = "Response: $result"
                )
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }
    
    fun showImagePicker() {
        // Image picker implementation
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

**Features:**
- Inherits from BaseViewModel for common functionalities
- State management with StateFlow
- Asynchronous operations with coroutines
- Network utilities integration
- Centralized error handling

### AboutScreen.kt - App Information

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
            // Header with back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navigator?.pop() }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "About",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Application information
            AboutContent(
                appName = "KMP Test App",
                version = "1.0.0",
                description = "Demonstration application for SweetMeSoft KMP Library. " +
                        "Shows the use of all components and functionalities " +
                        "available in the multiplatform library.",
                developers = listOf(
                    Developer(
                        name = "SweetMeSoft Team",
                        role = "Development",
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
                        title = "Documentation",
                        url = "https://erickvelasco11.github.io/KmpLibrary",
                        icon = Icons.Default.MenuBook
                    )
                )
            )
        }
    }
}
```

**Features:**
- Use of AboutContent component from library
- Back navigation
- Structured application information
- Links to external resources

## Themes and Styles

### Color.kt - Color Palette

```kotlin
val md_theme_light_primary = Color(0xFF6750A4)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFEADDFF)
val md_theme_light_onPrimaryContainer = Color(0xFF21005D)

val md_theme_dark_primary = Color(0xFFD0BCFF)
val md_theme_dark_onPrimary = Color(0xFF381E72)
val md_theme_dark_primaryContainer = Color(0xFF4F378B)
val md_theme_dark_onPrimaryContainer = Color(0xFFEADDFF)

// Custom colors
val SweetMeSoftBlue = Color(0xFF2196F3)
val SweetMeSoftGreen = Color(0xFF4CAF50)
val SweetMeSoftOrange = Color(0xFFFF9800)
```

### Theme.kt - Theme Configuration

```kotlin
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    // ... more colors
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    // ... more colors
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

**Features:**
- Light and dark theme support
- Dynamic colors on Android 12+
- Custom color palette
- Material Design 3 typography

## Usage Examples

### Example 1: Components Section

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
                text = "KMPControls Components",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Date selector
            ClickableOutlinedTextField(
                value = viewModel.uiState.value.selectedDate?.toString() ?: "",
                label = "Select Date",
                onClick = { viewModel.showDatePicker() },
                modifier = Modifier.fillMaxWidth()
            )
            
            // Time selector
            ClickableOutlinedTextField(
                value = viewModel.uiState.value.selectedTime?.toString() ?: "",
                label = "Select Time",
                onClick = { viewModel.showTimePicker() },
                modifier = Modifier.fillMaxWidth()
            )
            
            // Password control
            var password by remember { mutableStateOf("") }
            PasswordControl(
                value = password,
                onValueChange = { password = it },
                label = "Example Password",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
```

### Example 2: Maps Section

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
                text = "KMPMaps Components",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Multiplatform maps with Google Maps (Android) and MapKit (iOS)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Button(
                onClick = { viewModel.showMapExample() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Map, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Map Example")
            }
        }
    }
}
```

### Example 3: Utilities Section

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
                text = "Library Utilities",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Network test
            Button(
                onClick = { viewModel.testNetworkCall() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CloudDownload, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Test Network Call")
            }
            
            // Image selector
            Button(
                onClick = { viewModel.showImagePicker() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select Image")
            }
            
            // Validation example
            var email by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Test Email") },
                isError = email.isNotBlank() && !StringUtils.isValidEmail(email),
                supportingText = {
                    if (email.isNotBlank() && !StringUtils.isValidEmail(email)) {
                        Text("Invalid email")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
```

## How to Run

### Prerequisites

1. **Android Studio** (latest stable version)
2. **Xcode** (for iOS development)
3. **JDK 17** or higher
4. **Kotlin Multiplatform Mobile plugin**

### Steps to Run

#### Android

```bash
# Clone the repository
git clone https://github.com/erickvelasco11/KmpLibrary.git
cd KmpLibrary

# Run on Android
./gradlew :kmptestapp:installDebug

# Or from Android Studio
# 1. Open the project
# 2. Select 'kmptestapp' as module
# 3. Run on Android device/emulator
```

#### iOS

```bash
# Generate Xcode project
./gradlew :kmptestapp:embedAndSignAppleFrameworkForXcode

# Open in Xcode
open iosApp/iosApp.xcodeproj

# Or from Android Studio with KMP plugin
# 1. Select iOS configuration
# 2. Run on iOS simulator/device
```

### API Keys Configuration

To use maps functionalities, configure the API keys:

#### Android
```xml
<!-- android/src/main/res/values/strings.xml -->
<resources>
    <string name="google_maps_key">YOUR_API_KEY_HERE</string>
</resources>
```

#### iOS
```xml
<!-- iosApp/iosApp/Info.plist -->
<key>NSLocationWhenInUseUsageDescription</key>
<string>This app needs location access to show maps</string>
```
