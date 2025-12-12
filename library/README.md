# KMP Library

The `library` module is the core component of the SweetMeSoft KMP Library. It provides a robust architectural foundation and a comprehensive set of UI components, utilities, and network helpers for Kotlin Multiplatform development. It is designed to accelerate cross-platform application development by abstracting common patterns and boilerplate code.

## Installation

Add the dependency to your version catalog or build file.

### Version Catalog

```toml
[versions]
sweetmesoft = "1.7.7"

[libraries]
sweetmesoft-library = { module = "com.sweetmesoft:library", version.ref = "sweetmesoft" }
```

### Gradle (Kotlin DSL)

```kotlin
implementation(libs.sweetmesoft.library)
```

## Architecture

This library promotes a consistent architecture using a suite of `BaseScreen` composables and `BaseViewModel`.

### BaseScreen

`BaseScreen` provides a standardized screen structure with built-in support for:
- Navigation integration (Voyager)
- Top app bar customization
- Global loading state handling
- Global alert dialogs (Info, Confirm, Prompt, Progress)
- Floating Action Button (FAB) support

```kotlin
@Composable
fun HomeScreen() {
    BaseScreen(
        title = "Home",
        showTop = true,
        fabAction = { /* Handle FAB click */ }
    ) { paddingValues ->
        // Screen content
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("Welcome to the Home Screen")
        }
    }
}
```

### Advanced Screen Types

In addition to `BaseScreen`, the library offers specialized screen layouts:

#### BaseBottomBarScreen

Manages a screen with a bottom navigation bar. It requires a list of `BaseTab` items.

```kotlin
@Composable
fun MainScreen() {
    val homeTab = object : BaseTab {
        override val baseOptions = defaultBaseTabOptions(
            title = "Home",
            icon = rememberVectorPainter(Icons.Default.Home),
            showTop = true
        )
        
        @Composable
        override fun Content() {
            // Tab content
        }
    }

    BaseBottomBarScreen(
        tabs = listOf(homeTab, profileTab)
    )
}
```

#### BaseDrawerScreen

Manages a screen with a side navigation drawer. Similar to `BaseBottomBarScreen`, it uses `BaseTab` for navigation items.

```kotlin
@Composable
fun MainScreen() {
    BaseDrawerScreen(
        tabs = listOf(homeTab, settingsTab),
        headerView = {
            // Drawer header content
        },
        logoutAction = {
            // Handle logout
        }
    )
}
```

#### BaseBottomSheetScreen

A wrapper for screens displayed within a bottom sheet. It includes a standard top bar with a close button.

```kotlin
BaseBottomSheetScreen(
    title = "Details",
    showTop = true
) {
    // Bottom sheet content
}
```

### BaseViewModel

`BaseViewModel` serves as the base class for ViewModels, offering:
- Coroutine scope management
- Permission handling helpers
- Navigation integration

```kotlin
class HomeViewModel : BaseViewModel() {
    
    fun checkCameraPermission() {
        screenModelScope.launch {
            val granted = requestPermission(Permission.CAMERA)
            if (granted) {
                // Permission granted
            }
        }
    }
}
```

## Pre-built Screens

The library includes common screen implementations to speed up development.

### SplashContent

A configurable splash screen with logo animation and automatic navigation after a delay.

```kotlin
SplashContent(
    logo = painterResource(Res.drawable.logo),
    waitMillis = 2000,
    title = "My App",
    action = {
        // Navigate to next screen
        navigator.replace(HomeScreen())
    }
)
```

### AboutContent

An "About" screen displaying app version, logo, and links.

```kotlin
AboutContent(
    logo = painterResource(Res.drawable.logo),
    appName = "My App",
    appId = "com.example.myapp"
)
```

## UI Components

### Lists and Grids

The library provides `LocalList`/`RemoteList` for linear lists and `LocalGridList`/`RemoteGridList` for grid layouts.

#### LocalList / LocalGridList

For displaying static or locally available data.

```kotlin
LocalList(
    list = myDataList,
    title = "My Items",
    addClick = { /* Handle add */ }
) { index, item ->
    Text(text = item.name)
}

LocalGridList(
    list = myDataList,
    columns = 2
) { index, item ->
    MyGridItem(item)
}
```

#### RemoteList / RemoteGridList

Automatically handles data fetching, loading states, and pull-to-refresh from a URL.

```kotlin
RemoteList<MyDataItem>(
    url = "https://api.example.com/items",
    title = "Remote Items",
    bearer = "token",
    refresh = shouldRefresh
) { item ->
    Text(text = item.name)
}
```

### Dropdowns

`LocalDropDown` and `RemoteDropDown` simplify selection interfaces.

```kotlin
RemoteDropDown<MyDataItem>(
    url = "https://api.example.com/options",
    title = "Select Option",
    value = selectedOptionName,
    selectValue = { item -> 
        // Handle selection
    }
) { item ->
    Text(text = item.name)
}
```

### Other Components

#### SettingsItem

A standard row for settings screens with an icon, title, and description.

```kotlin
SettingsItem(
    icon = Icons.Default.Settings,
    title = "General",
    description = "App preferences"
) {
    // Handle click
}
```

#### ProfilePhoto

A circular image component that handles loading from a URL and caching.

```kotlin
ProfilePhoto(
    urlImage = "https://example.com/avatar.jpg",
    radius = 100.dp,
    onClick = { /* Handle click */ }
)
```

#### DoublePicker & CalculatorPopup

Input controls for numeric values.

```kotlin
DoublePicker(
    title = "Amount",
    value = amountStr,
    onValueChange = { newValue ->
        // Update value
    }
)
```

### Popups and Alerts

The `PopupHandler` allows displaying global alerts from anywhere in your code (e.g., ViewModels).

```kotlin
// Display a simple alert
PopupHandler.displayAlert(
    title = "Success",
    message = "Operation completed successfully"
)

// Display a confirmation dialog
PopupHandler.displayConfirm(
    title = "Delete Item",
    message = "Are you sure?",
    onConfirm = { 
        // Handle confirmation
    }
)
```

## Network Utilities

`NetworkUtils` provides simplified HTTP methods that integrate with the global loading state.

```kotlin
// GET request
val result = NetworkUtils.get<MyResponseData>(
    url = "https://api.example.com/data",
    showLoading = true, // specific loading indicator control
    bearer = "token"
)

result.onSuccess { response ->
    // Handle success
}

result.onFailure { error ->
    // Handle error
}

// POST request
val result = NetworkUtils.post<MyResponseData>(
    url = "https://api.example.com/data",
    body = myRequestObject
)
```

## Utilities

### PlatformUtils

Helper functions to interact with the platform.

```kotlin
val platform = getPlatform() // Returns PlatformType.ANDROID or PlatformType.IOS
val version = getAppVersion()
openAppStore("com.example.app") // Opens the store page
```

### General Utils

```kotlin
// String extensions
val isValid = "test@example.com".isEmail()
val passwordValid = isValidPassword("StrongPass1!")

// Browser
openUrl("https://www.google.com")
```

## Requirements

- **Android**: Min SDK 28, Target SDK 36
- **iOS**: iOS 14.0+
- **Kotlin**: 2.2.0+
- **Compose Multiplatform**: 1.9.0+
