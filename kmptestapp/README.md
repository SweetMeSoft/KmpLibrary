# KMPTestApp - Demo Application

KMPTestApp is a comprehensive demonstration application that showcases all the features and capabilities of the SweetMeSoft KMP Library. It serves as both a testing ground and a practical example of how to implement the library components in a real-world application.

## Overview

This application demonstrates:

- Complete integration of all library modules (Library, KMPControls, KMPMaps)
- Best practices for Kotlin Multiplatform development
- Modern UI/UX patterns with Compose Multiplatform
- Navigation patterns with Voyager
- State management and architecture patterns
- Network operations and data handling
- Custom theming and styling

## Features Demonstrated

### UI Components
- Date and time pickers from KMPControls
- Form validation and input controls
- Lists with local and remote data
- Dropdown components with search
- Alert and dialog systems
- Image loading and caching
- Navigation patterns

### Maps Integration
- Basic map display
- Marker management
- Location services
- Custom map styles
- Real-time location tracking

### Utilities
- Network requests and API integration
- String validation and formatting
- Number and currency formatting
- Date manipulation
- Image processing
- Haptic feedback

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

## Getting Started

### Prerequisites

1. **Android Studio** (latest stable version)
2. **Xcode** (for iOS development)
3. **JDK 17** or higher
4. **Kotlin Multiplatform Mobile plugin**

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/erickvelasco11/KmpLibrary.git
   cd KmpLibrary
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository
   - Select the root directory

3. **Sync the project**:
   ```bash
   ./gradlew build
   ```

### Running the Application

#### Android

```bash
# Run on Android device/emulator
./gradlew :kmptestapp:installDebug

# Or from Android Studio:
# 1. Select 'kmptestapp' configuration
# 2. Choose your target device
# 3. Click Run
```

#### iOS

```bash
# Generate Xcode project
./gradlew :kmptestapp:embedAndSignAppleFrameworkForXcode

# Open in Xcode
open iosApp/iosApp.xcodeproj

# Or from Android Studio with KMP plugin:
# 1. Select iOS configuration
# 2. Choose your target simulator/device
# 3. Click Run
```

#### Desktop (Experimental)

```bash
# Run desktop version
./gradlew :kmptestapp:run
```

## Configuration

### API Keys Setup

To use maps functionality, configure the required API keys:

#### Android

Add your Google Maps API key to `android/src/main/res/values/strings.xml`:

```xml
<resources>
    <string name="google_maps_key">YOUR_GOOGLE_MAPS_API_KEY</string>
</resources>
```

#### iOS

Add location permissions to `iosApp/iosApp/Info.plist`:

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>This app needs location access to show maps</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>This app needs location access to show maps</string>
```

### Dependencies

The app uses the following main dependencies:

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

## Key Examples

### 1. Main Screen Implementation

The main screen demonstrates the use of multiple components:

```kotlin
class MainScreen : BaseBottomBarScreen() {
    @Composable
    override fun ScreenContent() {
        val viewModel = remember { MainViewModel() }
        val uiState by viewModel.uiState.collectAsState()
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { ComponentsSection(viewModel) }
            item { MapsSection(viewModel) }
            item { UtilitiesSection(viewModel) }
        }
    }
}
```

### 2. Form Validation Example

```kotlin
@Composable
fun UserFormExample() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val isFormValid = remember(name, email, password) {
        name.isNotBlank() && 
        StringUtils.isValidEmail(email) && 
        password.length >= 6
    }
    
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = email.isNotBlank() && !StringUtils.isValidEmail(email),
            modifier = Modifier.fillMaxWidth()
        )
        
        PasswordControl(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            supportingText = if (password.isNotBlank() && password.length < 6) {
                "Password must be at least 6 characters"
            } else null
        )
        
        Button(
            onClick = { /* Submit form */ },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}
```

### 3. Network Integration

```kotlin
class MainViewModel : BaseViewModel() {
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
}
```

## Architecture Patterns

### MVVM Pattern

The app follows the Model-View-ViewModel pattern:

- **Model**: Data classes and repository patterns
- **View**: Composable functions for UI
- **ViewModel**: Business logic and state management

### Navigation

Uses Voyager for type-safe navigation:

```kotlin
@Composable
fun App() {
    AppTheme {
        Navigator(screen = SplashScreen()) { navigator ->
            BaseViewModel.navigator = navigator
            SlideTransition(navigator)
        }
    }
}
```

### State Management

Utilizes StateFlow for reactive state management:

```kotlin
class MainViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
}
```

## Testing

The app includes examples of:

- Unit testing with Kotlin Test
- UI testing with Compose Testing
- Integration testing across platforms

## Customization

### Themes

Custom themes are defined in the `theme` package:

```kotlin
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

### Adding New Features

1. Create new screen in `screens` package
2. Add navigation logic
3. Implement ViewModel if needed
4. Add to main navigation

## Troubleshooting

### Common Issues

1. **Build errors**: Ensure all dependencies are properly synced
2. **iOS build issues**: Check Xcode version compatibility
3. **Maps not showing**: Verify API keys are correctly configured
4. **Network errors**: Check internet connectivity and API endpoints

### Getting Help

- [Main Documentation](../docs/)
- [GitHub Issues](https://github.com/erickvelasco11/KmpLibrary/issues)
- [Contributing Guide](../CONTRIBUTING.md)

## Contributing

Contributions are welcome! Please read the [Contributing Guide](../CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is part of the SweetMeSoft KMP Library suite. See the [LICENSE](../LICENSE) file for details.