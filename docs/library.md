# 📙 Library Documentation

Library is the main module of the SweetMeSoft KMP library that provides advanced UI components, navigation tools, network utilities and complete functionalities for cross-platform application development.

## 📋 Table of Contents

- [Installation](#-installation)
- [Base Architecture](#-base-architecture)
- [UI Components](#-ui-components)
- [Advanced Controls](#-advanced-controls)
- [Alerts and Dialogs](#-alerts-and-dialogs)
- [Lists and Grids](#-lists-and-grids)
- [Utilities](#-utilities)
- [Navigation](#-navigation)
- [Usage Examples](#-usage-examples)

## 🚀 Installation

```kotlin
commonMain.dependencies {
    implementation("com.sweetmesoft.kmplibrary:kmplibrary:1.6.6")
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:1.6.6")
    
    // Required dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-beta03")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.1.0-beta03")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:1.1.0-beta03")
    implementation("cafe.adriel.voyager:voyager-transitions:1.1.0-beta03")
    implementation("io.ktor:ktor-client-core:3.2.3")
    implementation("media.kamel:kamel-image-default:1.0.7")
    implementation("io.github.alexzhirkevich:compottie:2.0.0-rc04")
}
```

## 🏗️ Base Architecture

### BaseViewModel
Base class for ViewModels with common functionalities.

```kotlin
abstract class BaseViewModel : ViewModel() {
    companion object {
        var navigator: Navigator? = null
    }
    
    protected val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    protected val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
    
    protected fun setError(error: String?) {
        _error.value = error
    }
    
    fun navigateTo(screen: Screen) {
        navigator?.push(screen)
    }
    
    fun navigateBack() {
        navigator?.pop()
    }
}
```

### BaseScreen
Base screen with common functionalities.

```kotlin
abstract class BaseScreen : Screen {
    @Composable
    abstract fun ScreenContent()
    
    @Composable
    override fun Content() {
        ScreenContent()
    }
}
```

### BaseBottomBarScreen
Base screen with bottom navigation bar.

```kotlin
abstract class BaseBottomBarScreen : Screen {
    @Composable
    abstract fun ScreenContent()
    
    @Composable
    abstract fun BottomBarContent()
    
    @Composable
    override fun Content() {
        Scaffold(
            bottomBar = { BottomBarContent() }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                ScreenContent()
            }
        }
    }
}
```

## 🎨 UI Components

### PasswordControl
Password control with toggleable visibility.

```kotlin
@Composable
fun PasswordControl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null
)
```

### SearchControl
Search control with advanced functionalities.

```kotlin
@Composable
fun SearchControl(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    enabled: Boolean = true,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {}
)
```

### ProfilePhoto
Component for displaying and editing profile photos.

```kotlin
@Composable
fun ProfilePhoto(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    onClick: (() -> Unit)? = null,
    placeholder: ImageBitmap? = null,
    isEditable: Boolean = false
)
```

### LoadingView
Customizable loading indicator.

```kotlin
@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    message: String = "Loading...",
    showMessage: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary
)
```

### MoreControl
Options menu control (three dots).

```kotlin
@Composable
fun MoreControl(
    options: List<IconAction>,
    modifier: Modifier = Modifier,
    onOptionSelected: (IconAction) -> Unit
)

data class IconAction(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val enabled: Boolean = true
)
```

## 🚨 Alerts and Dialogs

### PopupHandler
Global handler for popups and alerts.

```kotlin
object PopupHandler {
    private var _currentPopup by mutableStateOf<PopupState?>(null)
    val currentPopup: PopupState? get() = _currentPopup
    
    fun showAlert(
        title: String,
        message: String,
        onConfirm: () -> Unit = {},
        onCancel: () -> Unit = {}
    )
    
    fun showProgress(
        title: String,
        message: String,
        progress: Float? = null
    )
    
    fun showPrompt(
        title: String,
        message: String,
        initialValue: String = "",
        onConfirm: (String) -> Unit,
        onCancel: () -> Unit = {}
    )
    
    fun dismiss()
}
```

### AlertConfirm
Confirmation dialog.

```kotlin
@Composable
fun AlertConfirm(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    confirmText: String = "Accept",
    cancelText: String = "Cancel",
    showDialog: Boolean = true
)
```

### AlertProgress
Progress dialog.

```kotlin
@Composable
fun AlertProgress(
    title: String,
    message: String,
    progress: Float? = null, // null for indeterminate progress
    showDialog: Boolean = true,
    onCancel: (() -> Unit)? = null
)
```

### AlertPrompt
Text input dialog.

```kotlin
@Composable
fun AlertPrompt(
    title: String,
    message: String,
    initialValue: String = "",
    placeholder: String = "",
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    showDialog: Boolean = true
)
```

## 📋 Lists and Grids

### LocalList
Local list with static data.

```kotlin
@Composable
fun <T> LocalList(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
    emptyContent: @Composable () -> Unit = { EmptyList() },
    loadingContent: @Composable () -> Unit = { LoadingView() }
)
```

### RemoteList
List with remote data and pagination.

```kotlin
@Composable
fun <T> RemoteList(
    items: List<T>,
    isLoading: Boolean,
    hasMoreItems: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
    emptyContent: @Composable () -> Unit = { EmptyList() },
    loadingContent: @Composable () -> Unit = { LoadingView() }
)
```

### LocalGridList
Local grid with static data.

```kotlin
@Composable
fun <T> LocalGridList(
    items: List<T>,
    columns: Int = 2,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
    emptyContent: @Composable () -> Unit = { EmptyList() }
)
```

### RemoteGridList
Grid with remote data.

```kotlin
@Composable
fun <T> RemoteGridList(
    items: List<T>,
    columns: Int = 2,
    isLoading: Boolean,
    hasMoreItems: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
)
```

## 🔽 Dropdowns

### CommonDropDown
Basic dropdown with local elements.

```kotlin
@Composable
fun <T> CommonDropDown(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    enabled: Boolean = true,
    itemText: (T) -> String = { it.toString() }
)
```

### RemoteDropDown
Dropdown with remote data.

```kotlin
@Composable
fun <T> RemoteDropDown(
    url: String,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    parser: (String) -> List<T>,
    itemText: (T) -> String = { it.toString() }
)
```

## 🛠️ Utilities

### NetworkUtils
Utilities for network operations.

```kotlin
object NetworkUtils {
    suspend fun get(url: String): String
    suspend fun post(url: String, body: String): String
    suspend fun put(url: String, body: String): String
    suspend fun delete(url: String): String
    
    fun createHttpClient(): HttpClient
    fun isNetworkAvailable(): Boolean
}
```

### ImageUtils
Utilities for image handling.

```kotlin
object ImageUtils {
    fun resizeImage(image: ImageBitmap, width: Int, height: Int): ImageBitmap
    fun cropImage(image: ImageBitmap, x: Int, y: Int, width: Int, height: Int): ImageBitmap
    fun rotateImage(image: ImageBitmap, degrees: Float): ImageBitmap
    fun imageToBase64(image: ImageBitmap): String
    fun base64ToImage(base64: String): ImageBitmap?
}
```

### StringUtils
Utilities for string handling.

```kotlin
object StringUtils {
    fun isValidEmail(email: String): Boolean
    fun isValidPhone(phone: String): Boolean
    fun formatPhone(phone: String): String
    fun removeAccents(text: String): String
    fun capitalizeWords(text: String): String
    fun truncate(text: String, maxLength: Int): String
}
```

### FileUtils
Utilities for file handling.

```kotlin
object FileUtils {
    suspend fun readTextFile(path: String): String?
    suspend fun writeTextFile(path: String, content: String): Boolean
    suspend fun deleteFile(path: String): Boolean
    suspend fun fileExists(path: String): Boolean
    fun getFileExtension(filename: String): String
    fun getMimeType(filename: String): String
}
```

## 📱 Predefined Screens

### SplashContent
Customizable splash screen.

```kotlin
@Composable
fun SplashContent(
    logo: ImageBitmap? = null,
    title: String = "",
    subtitle: String = "",
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onSplashComplete: () -> Unit
)
```

### AboutContent
App information screen.

```kotlin
@Composable
fun AboutContent(
    appName: String,
    version: String,
    description: String,
    logo: ImageBitmap? = null,
    developers: List<Developer> = emptyList(),
    links: List<AppLink> = emptyList()
)
```

## 📝 Usage Examples

### Example 1: Complete Application with Navigation

```kotlin
@Composable
fun MyApp() {
    MyAppTheme {
        Navigator(screen = SplashScreen()) { navigator ->
            BaseViewModel.navigator = navigator
            SlideTransition(navigator)
        }
    }
}

class SplashScreen : BaseScreen() {
    @Composable
    override fun ScreenContent() {
        SplashContent(
            title = "My App",
            subtitle = "Welcome",
            onSplashComplete = {
                navigateTo(MainScreen())
            }
        )
    }
}

class MainScreen : BaseBottomBarScreen() {
    @Composable
    override fun ScreenContent() {
        var searchQuery by remember { mutableStateOf("") }
        var items by remember { mutableStateOf(emptyList<String>()) }
        
        Column {
            SearchControl(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { query ->
                    // Perform search
                    items = performSearch(query)
                }
            )
            
            LocalList(
                items = items,
                itemContent = { item ->
                    ListItem(
                        headlineContent = { Text(item) }
                    )
                }
            )
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

### Example 2: Form with Validation

```kotlin
@Composable
fun UserFormExample() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    
    val isFormValid = remember(name, email, password) {
        name.isNotBlank() && 
        StringUtils.isValidEmail(email) && 
        password.length >= 6
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
        
        RemoteDropDown(
            url = "https://api.example.com/countries",
            selectedItem = selectedCountry,
            onItemSelected = { selectedCountry = it },
            label = "Country",
            parser = { json -> parseCountries(json) },
            itemText = { it.name }
        )
        
        Button(
            onClick = {
                if (isFormValid) {
                    submitForm(name, email, password, selectedCountry)
                }
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}
```

### Example 3: List with Remote Data

```kotlin
@Composable
fun ProductListExample() {
    var products by remember { mutableStateOf(emptyList<Product>()) }
    var isLoading by remember { mutableStateOf(false) }
    var hasMoreItems by remember { mutableStateOf(true) }
    var page by remember { mutableStateOf(1) }
    
    LaunchedEffect(Unit) {
        loadProducts(page)
    }
    
    RemoteList(
        items = products,
        isLoading = isLoading,
        hasMoreItems = hasMoreItems,
        onLoadMore = {
            if (!isLoading && hasMoreItems) {
                loadProducts(page + 1)
            }
        },
        itemContent = { product ->
            ProductItem(
                product = product,
                onClick = {
                    // Navigate to product details
                }
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
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            ProfilePhoto(
                imageUrl = product.imageUrl,
                size = 60.dp
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = NumberUtils.formatCurrency(product.price),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            MoreControl(
                options = listOf(
                    IconAction("edit", "Edit", Icons.Default.Edit),
                    IconAction("delete", "Delete", Icons.Default.Delete)
                ),
                onOptionSelected = { action ->
                    when (action.id) {
                        "edit" -> editProduct(product)
                        "delete" -> deleteProduct(product)
                    }
                }
            )
        }
    }
}
```

## 🎨 Advanced Customization

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
            secondary = Color(0xFF03DAC6),
            background = Color(0xFF121212)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFFF5F5F5)
        )
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(
            headlineLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
        ),
        content = content
    )
}
```

## 📚 Additional Resources

- [Voyager Navigation](https://github.com/adrielcafe/voyager)
- [Ktor Client](https://ktor.io/docs/client.html)
- [Kamel Image Loading](https://github.com/Kamel-Media/Kamel)
- [Compottie Animations](https://github.com/alexzhirkevich/compottie)
- [Complete Examples](../kmptestapp/)
- [Report Issues](https://github.com/erickvelasco11/KmpLibrary/issues)

---

**Upcoming Features:**
- More UI components
- Local database integration
- Push notification support
- Analytics tools
- More network utilities