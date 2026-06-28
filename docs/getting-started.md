---
layout: default
title: Getting Started
nav_order: 2
---

# Getting Started Guide

This guide walks you through integrating and using the SweetMeSoft KMP Library in a new Kotlin Multiplatform (KMP) project.

---

## 1. Prerequisites and Versions

Ensure your KMP project aligns with the library's target environment versions:

- **Kotlin Version**: `2.4.0`
- **Compose Multiplatform**: `1.11.1`
- **Android Gradle Plugin (AGP)**: `9.2.1`
- **Android Target SDK**: `37` (Compile SDK: `37`, Min SDK: `24`)
- **iOS Target**: iOS `12.0` or higher

---

## 2. Installation & Configuration

### A. Version Catalog (`gradle/libs.versions.toml`)

Open your `libs.versions.toml` file and add the SweetMeSoft library declarations and required AndroidX Lifecycle transitive dependencies:

```toml
[versions]
sweetmesoft = "2.0.1"
androidx-lifecycle = "2.11.0"
compose-multiplatform = "1.9.0"
compose-plugin = "1.11.1"

[libraries]
sweetmesoft-kmpbase = { module = "com.sweetmesoft.kmpbase:kmpbase", version.ref = "sweetmesoft" }
sweetmesoft-kmpcontrols = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
sweetmesoft-kmpmaps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }

# Required Lifecycle libraries
androidx-lifecycle-viewmodel = { group = "androidx.lifecycle", name = "lifecycle-viewmodel", version.ref = "androidx-lifecycle" }
androidx-lifecycle-runtime-compose = { group = "androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "androidx-lifecycle" }
```

### B. Declaring Module Dependencies (`build.gradle.kts` - Shared Module)

In your shared multiplatform module (usually `:shared` or `:composeApp`), add the dependencies under the `commonMain` source set:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.sweetmesoft.kmpbase)
            implementation(libs.sweetmesoft.kmpcontrols)
            implementation(libs.sweetmesoft.kmpmaps)
        }
    }
}
```

---

## 3. Voyager Navigation Integration

The architecture utilizes Voyager for type-safe multiplatform navigation. Connect Voyager's `Navigator` to the core libraries inside your main shared application entry point:

```kotlin
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.sweetmesoft.kmpbase.base.BaseViewModel
import com.sweetmesoft.kmpbase.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Navigator(screen = LoginScreen()) { navigator ->
            // Connect navigator to BaseViewModel to allow navigation from VM layers
            BaseViewModel.navigator = navigator
            
            // Add transitions
            SlideTransition(navigator)
        }
    }
}
```

---

## 4. Implementing Screens & ViewModels

Ensure all presentation screens and business logic models inherit from `BaseScreen` and `BaseViewModel` respectively.

### A. ViewModels (`BaseViewModel`)

Subclass `BaseViewModel` to handle standard loading operations, handle network routines safely within coroutines, and present alerts.

```kotlin
import com.sweetmesoft.kmpbase.base.BaseViewModel
import com.sweetmesoft.kmpbase.controls.alerts.PopupHandler
import com.sweetmesoft.kmpbase.tools.NetworkUtils
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    fun executeLogin(credentials: UserCredentials) {
        viewModelScope.launch {
            // Trigger global loading spinner overlay
            setLoading(true)
            
            val result = NetworkUtils.post<LoginResponse>(
                url = "https://api.example.com/login",
                body = credentials
            )
            
            setLoading(false)
            
            result.onSuccess { response ->
                // Navigate to home screen
                navigator?.replace(HomeScreen())
            }.onFailure { exception ->
                // Trigger global error alert dialog from VM layer
                PopupHandler.displayAlert(
                    title = "Authentication Error",
                    message = exception.message ?: "Failed to log in."
                )
            }
        }
    }
}
```

### B. Screens (`BaseScreen`)

Subclass `BaseScreen` (or advanced layouts like `BaseBottomBarScreen`, `BaseDrawerScreen`, or `BaseBottomSheetScreen`) to build layouts. The base screen automatically overlays global alert dialogs and progress sheets.

```kotlin
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.controls.LoadingView

class MainDashboardScreen : BaseScreen(
    title = "Dashboard",
    showTop = true // Shows the standard Toolbar header
) {
    @Composable
    override fun ScreenContent() {
        val viewModel = rememberScreenModel { MainDashboardViewModel() }
        
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Welcome to KMP dashboard!")
            }
            
            // Required overlay to show global loader triggered by setLoading(true)
            LoadingView()
        }
    }
}
```

---

## 5. Usage of Shared Core Components

### A. List Configurations (`RemoteList`)

Easily bind network data structures directly to composable list canvases:

```kotlin
import com.sweetmesoft.kmpbase.controls.commonList.RemoteList

RemoteList<Product>(
    url = "https://api.example.com/products",
    title = "Available Products",
    bearer = "session_token"
) { product ->
    ProductRow(product)
}
```

### B. Map Components (`MapComponent`)

Add a platform-specific map component (Google Maps on Android, MapKit on iOS) with custom marker positions:

```kotlin
import com.sweetmesoft.kmpmaps.MapComponent
import com.sweetmesoft.kmpmaps.controls.Coordinates
import com.sweetmesoft.kmpmaps.controls.GeoPosition
import com.sweetmesoft.kmpmaps.controls.MarkerMap

val points = listOf(
    GeoPosition(
        coordinates = Coordinates(37.7749, -122.4194),
        markerMap = MarkerMap(title = "SF Headquarters")
    )
)

MapComponent(
    coordinates = Coordinates(37.7749, -122.4194),
    zoom = 12f,
    markers = points
)
```

### C. Picker Controls (`DatePicker`)

```kotlin
import com.sweetmesoft.kmpcontrols.pickers.DatePicker
import kotlinx.datetime.LocalDate

var selectedDate by remember { mutableStateOf(LocalDate(2026, 6, 28)) }

DatePicker(
    value = selectedDate,
    title = "Select Date",
    onSelectedDate = { date ->
        selectedDate = date
    }
)
```
