# 📙 Library Documentation

Library es el módulo principal de la librería SweetMeSoft KMP que proporciona componentes UI avanzados, herramientas de navegación, utilidades de red y funcionalidades completas para el desarrollo de aplicaciones multiplataforma.

## 📋 Tabla de Contenidos

- [Instalación](#instalación)
- [Arquitectura Base](#arquitectura-base)
- [Componentes UI](#componentes-ui)
- [Controles Avanzados](#controles-avanzados)
- [Alertas y Diálogos](#alertas-y-diálogos)
- [Listas y Grids](#listas-y-grids)
- [Utilidades](#utilidades)
- [Navegación](#navegación)
- [Ejemplos de Uso](#ejemplos-de-uso)

## 🚀 Instalación

```kotlin
commonMain.dependencies {
    implementation("com.sweetmesoft.kmplibrary:kmplibrary:1.6.6")
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:1.6.6")
    
    // Dependencias requeridas
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

## 🏗️ Arquitectura Base

### BaseViewModel
Clase base para ViewModels con funcionalidades comunes.

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
Pantalla base con funcionalidades comunes.

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
Pantalla base con barra de navegación inferior.

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

## 🎨 Componentes UI

### PasswordControl
Control de contraseña con visibilidad toggleable.

```kotlin
@Composable
fun PasswordControl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Contraseña",
    placeholder: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null
)
```

### SearchControl
Control de búsqueda con funcionalidades avanzadas.

```kotlin
@Composable
fun SearchControl(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar...",
    enabled: Boolean = true,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {}
)
```

### ProfilePhoto
Componente para mostrar y editar fotos de perfil.

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
Indicador de carga personalizable.

```kotlin
@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    message: String = "Cargando...",
    showMessage: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary
)
```

### MoreControl
Control de menú de opciones (tres puntos).

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

## 🚨 Alertas y Diálogos

### PopupHandler
Manejador global de popups y alertas.

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
Diálogo de confirmación.

```kotlin
@Composable
fun AlertConfirm(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    confirmText: String = "Aceptar",
    cancelText: String = "Cancelar",
    showDialog: Boolean = true
)
```

### AlertProgress
Diálogo de progreso.

```kotlin
@Composable
fun AlertProgress(
    title: String,
    message: String,
    progress: Float? = null, // null para progreso indeterminado
    showDialog: Boolean = true,
    onCancel: (() -> Unit)? = null
)
```

### AlertPrompt
Diálogo de entrada de texto.

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

## 📋 Listas y Grids

### LocalList
Lista local con datos estáticos.

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
Lista con datos remotos y paginación.

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
Grid local con datos estáticos.

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
Grid con datos remotos.

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
Dropdown básico con elementos locales.

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
Dropdown con datos remotos.

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

## 🛠️ Utilidades

### NetworkUtils
Utilidades para operaciones de red.

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
Utilidades para manejo de imágenes.

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
Utilidades para manejo de strings.

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
Utilidades para manejo de archivos.

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

## 📱 Pantallas Predefinidas

### SplashContent
Pantalla de splash personalizable.

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
Pantalla de información de la app.

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

## 📝 Ejemplos de Uso

### Ejemplo 1: Aplicación Completa con Navegación

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
            title = "Mi App",
            subtitle = "Bienvenido",
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
                    // Realizar búsqueda
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
                label = { Text("Inicio") }
            )
        }
    }
}
```

### Ejemplo 2: Formulario con Validación

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
            label = { Text("Nombre") },
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
            label = "Contraseña",
            supportingText = if (password.isNotBlank() && password.length < 6) {
                "La contraseña debe tener al menos 6 caracteres"
            } else null
        )
        
        RemoteDropDown(
            url = "https://api.example.com/countries",
            selectedItem = selectedCountry,
            onItemSelected = { selectedCountry = it },
            label = "País",
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
            Text("Registrarse")
        }
    }
}
```

### Ejemplo 3: Lista con Datos Remotos

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
                    // Navegar a detalles del producto
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
                    IconAction("edit", "Editar", Icons.Default.Edit),
                    IconAction("delete", "Eliminar", Icons.Default.Delete)
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

## 🎨 Personalización Avanzada

### Temas Personalizados

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

## 📚 Recursos Adicionales

- [Voyager Navigation](https://github.com/adrielcafe/voyager)
- [Ktor Client](https://ktor.io/docs/client.html)
- [Kamel Image Loading](https://github.com/Kamel-Media/Kamel)
- [Compottie Animations](https://github.com/alexzhirkevich/compottie)
- [Ejemplos Completos](../kmptestapp/)
- [Reportar Issues](https://github.com/erickvelasco11/KmpLibrary/issues)

---

**Próximas Características:**
- Más componentes UI
- Integración con bases de datos locales
- Soporte para notificaciones push
- Herramientas de analytics
- Más utilidades de red