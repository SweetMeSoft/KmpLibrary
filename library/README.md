# Library - Core UI Components and Utilities

The Library module is the core component of the SweetMeSoft KMP Library, providing essential UI components, utilities, and architectural foundations for Kotlin Multiplatform applications.

## Features

### UI Components
- **Form Controls**: Advanced text fields, password controls, and validation
- **Lists and Grids**: Local and remote data lists with search and filtering
- **Dropdowns**: Local and remote dropdown components
- **Alerts and Dialogs**: Customizable alert system with multiple types
- **Navigation**: Bottom bar screens and navigation utilities
- **Images**: Advanced image loading with caching and transformations

### Utilities
- **Network Utils**: HTTP client with request/response handling
- **String Utils**: Email validation, formatting, and text processing
- **Number Utils**: Currency formatting and number operations
- **Date Utils**: Date formatting and manipulation
- **Image Utils**: Image processing and optimization
- **Vibration**: Cross-platform haptic feedback

### Architecture
- **Base Classes**: ViewModel, Screen, and Component foundations
- **State Management**: Reactive state handling with StateFlow
- **Error Handling**: Centralized error management
- **Popup System**: Global popup and alert management

## Installation

### 1. Add Dependencies

Update your `libs.versions.toml` and `build.gradle.kts` files:

#### `libs.versions.toml`

```toml
[versions]
sweetmesoft = "{version-sweetmesoft}"
voyager = "1.0.0"
ktor = "2.3.0"
kamel = "0.7.0"

[libraries]
sweetmesoft-library = { module = "com.sweetmesoft.library:library", version.ref = "sweetmesoft" }
voyager-navigator = { module = "cafe.adriel.voyager:voyager-navigator", version.ref = "voyager" }
voyager-bottom-sheet-navigator = { module = "cafe.adriel.voyager:voyager-bottom-sheet-navigator", version.ref = "voyager" }
voyager-tab-navigator = { module = "cafe.adriel.voyager:voyager-tab-navigator", version.ref = "voyager" }
voyager-transitions = { module = "cafe.adriel.voyager:voyager-transitions", version.ref = "voyager" }
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
kamel-image = { module = "media.kamel:kamel-image", version.ref = "kamel" }
```

#### `build.gradle.kts`

```kotlin
implementation(libs.sweetmesoft.library)
implementation(libs.voyager.navigator)
implementation(libs.voyager.bottom.sheet.navigator)
implementation(libs.voyager.tab.navigator)
implementation(libs.voyager.transitions)
implementation(libs.ktor.client.core)
implementation(libs.ktor.client.content.negotiation)
implementation(libs.ktor.serialization.kotlinx.json)
implementation(libs.kamel.image)
```

### 2. Platform-Specific Setup

#### Android

Add internet permission to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.VIBRATE" />
```

#### iOS

No additional setup required for basic functionality.

## Basic Usage

### Creating a Screen with Navigation

```kotlin
class MainScreen : BaseBottomBarScreen() {
    @Composable
    override fun ScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome to My App",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Button(
                onClick = { navigator?.push(DetailScreen()) }
            ) {
                Text("Go to Details")
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
        }
    }
}
```

### Using Form Controls

```kotlin
@Composable
fun UserForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
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
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

### Working with Lists

```kotlin
@Composable
fun ProductList() {
    val products = remember {
        listOf(
            Product("1", "iPhone 15", "Latest Apple smartphone", 999.99),
            Product("2", "Samsung Galaxy S24", "Android flagship", 899.99),
            Product("3", "Google Pixel 8", "Pure Android experience", 699.99)
        )
    }
    
    LocalList(
        items = products,
        itemContent = { product ->
            ProductItem(
                product = product,
                onClick = { /* Handle click */ }
            )
        }
    )
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = NumberUtils.formatCurrency(product.price),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
```

### Using Alerts and Dialogs

```kotlin
@Composable
fun AlertExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                PopupHandler.showAlert(
                    title = "Success",
                    message = "Operation completed successfully!"
                )
            }
        ) {
            Text("Show Success Alert")
        }
        
        Button(
            onClick = {
                PopupHandler.showConfirm(
                    title = "Confirm Action",
                    message = "Are you sure you want to delete this item?",
                    onConfirm = {
                        // Handle confirmation
                    },
                    onCancel = {
                        // Handle cancellation
                    }
                )
            }
        ) {
            Text("Show Confirmation")
        }
    }
}
```

### Network Operations

```kotlin
class DataRepository {
    suspend fun fetchUsers(): List<User> {
        return try {
            val response = NetworkUtils.get("https://api.example.com/users")
            Json.decodeFromString<List<User>>(response)
        } catch (e: Exception) {
            PopupHandler.showError("Failed to fetch users: ${e.message}")
            emptyList()
        }
    }
    
    suspend fun createUser(user: User): Boolean {
        return try {
            val json = Json.encodeToString(user)
            NetworkUtils.post("https://api.example.com/users", json)
            true
        } catch (e: Exception) {
            PopupHandler.showError("Failed to create user: ${e.message}")
            false
        }
    }
}
```

### Using Utilities

```kotlin
// String validation
val isValidEmail = StringUtils.isValidEmail("user@example.com")
val cleanedText = StringUtils.removeSpecialCharacters("Hello, World!")

// Number formatting
val formattedPrice = NumberUtils.formatCurrency(1234.56) // "$1,234.56"
val formattedNumber = NumberUtils.formatWithCommas(1000000) // "1,000,000"

// Date operations
val formattedDate = DateUtils.formatDate(Clock.System.now(), "yyyy-MM-dd")
val isWeekend = DateUtils.isWeekend(Clock.System.now())

// Vibration
Vibrator.vibrate(VibrationPattern.SHORT)
Vibrator.vibrate(VibrationPattern.DOUBLE_CLICK)
```

## Architecture Patterns

### Using BaseViewModel

```kotlin
class UserViewModel : BaseViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
    
    fun loadUsers() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val userList = userRepository.fetchUsers()
                _users.value = userList
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }
}
```

### Custom Themes

```kotlin
@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6)
        )
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
```

## API Reference

For complete API documentation, visit the [Library Documentation](../docs/library.md).

## Requirements

- Kotlin Multiplatform 1.9.0+
- Android API 28+
- iOS 12.0+
- Compose Multiplatform 1.5.0+

## License

This library is part of the SweetMeSoft KMP Library suite. See the main project LICENSE file for details.