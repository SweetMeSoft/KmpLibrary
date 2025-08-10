# 📘 KMPControls Documentation

KMPControls es el módulo fundamental de la librería SweetMeSoft KMP que proporciona componentes UI básicos y utilidades esenciales para el desarrollo multiplataforma.

## 📋 Tabla de Contenidos

- [Instalación](#instalación)
- [Componentes Disponibles](#componentes-disponibles)
- [Controles](#controles)
- [Diálogos](#diálogos)
- [Selectores](#selectores)
- [Utilidades](#utilidades)
- [Ejemplos de Uso](#ejemplos-de-uso)

## 🚀 Instalación

```kotlin
commonMain.dependencies {
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:1.6.6")
    
    // Dependencias requeridas
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}
```

## 🎛️ Componentes Disponibles

### Controles

#### ClickableOutlinedTextField
Campo de texto que actúa como botón, ideal para selectores y navegación.

```kotlin
@Composable
fun ClickableOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    color: Color,
    enabled: Boolean = true,
    onClick: () -> Unit
)
```

**Ejemplo de uso:**
```kotlin
ClickableOutlinedTextField(
    value = selectedDate,
    title = "Seleccionar Fecha",
    color = MaterialTheme.colorScheme.primary,
    onClick = { showDatePicker = true }
)
```

### 🗨️ Diálogos

#### BaseDialog
Diálogo base personalizable para crear modales consistentes.

```kotlin
@Composable
fun BaseDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String? = null,
    content: @Composable () -> Unit
)
```

#### CalendarDialog
Diálogo con calendario integrado para selección de fechas.

```kotlin
@Composable
fun CalendarDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
)
```

#### ClockDialog
Diálogo para selección de hora con interfaz intuitiva.

```kotlin
@Composable
fun ClockDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    initialTime: LocalTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
)
```

#### DialogFooter
Pie de diálogo estandarizado con botones de acción.

```kotlin
@Composable
fun DialogFooter(
    onCancel: () -> Unit,
    onAccept: () -> Unit,
    cancelText: String = "Cancelar",
    acceptText: String = "Aceptar",
    acceptEnabled: Boolean = true
)
```

### 📅 Selectores (Pickers)

#### DatePicker
Selector de fecha con interfaz Material Design.

```kotlin
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    label: String = "Fecha",
    format: String = "dd/MM/yyyy",
    enabled: Boolean = true
)
```

#### TimePicker
Selector de hora con formato personalizable.

```kotlin
@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    label: String = "Hora",
    format: String = "HH:mm",
    enabled: Boolean = true
)
```

#### DateTimePicker
Selector combinado de fecha y hora.

```kotlin
@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    selectedDateTime: String,
    onDateTimeSelected: (String) -> Unit,
    label: String = "Fecha y Hora",
    dateFormat: String = "dd/MM/yyyy",
    timeFormat: String = "HH:mm",
    enabled: Boolean = true
)
```

### 🔧 Utilidades

#### DateUtils
Utilidades para manejo y formateo de fechas.

```kotlin
object DateUtils {
    fun formatDate(date: LocalDate, pattern: String): String
    fun parseDate(dateString: String, pattern: String): LocalDate?
    fun getCurrentDate(): LocalDate
    fun addDays(date: LocalDate, days: Int): LocalDate
    fun getDayOfWeek(date: LocalDate): String
    fun getMonthName(month: Int): String
}
```

#### NumberUtils
Utilidades para validación y formateo de números.

```kotlin
object NumberUtils {
    fun formatCurrency(amount: Double, currency: String = "$"): String
    fun formatPercentage(value: Double): String
    fun isValidNumber(text: String): Boolean
    fun parseDouble(text: String): Double?
    fun formatDecimal(value: Double, decimals: Int): String
}
```

#### Vibrator
Utilidad para feedback háptico multiplataforma.

```kotlin
object Vibrator {
    fun vibrate(duration: Long = 100)
    fun vibratePattern(pattern: LongArray)
    fun cancel()
}
```

### 🎨 Objetos de Estilo

#### Colors
Paleta de colores predefinida para la librería.

```kotlin
object Colors {
    val Primary = Color(0xFF6200EE)
    val PrimaryVariant = Color(0xFF3700B3)
    val Secondary = Color(0xFF03DAC6)
    val Background = Color(0xFFF5F5F5)
    val Surface = Color(0xFFFFFFFF)
    val Error = Color(0xFFB00020)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFF000000)
    val OnBackground = Color(0xFF000000)
    val OnSurface = Color(0xFF000000)
    val OnError = Color(0xFFFFFFFF)
}
```

## 📝 Ejemplos de Uso

### Ejemplo Completo: Formulario con Validación

```kotlin
@Composable
fun UserFormScreen() {
    var name by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo de nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Selector de fecha
        DatePicker(
            selectedDate = birthDate,
            onDateSelected = { birthDate = it },
            label = "Fecha de Nacimiento"
        )
        
        // Selector de hora
        TimePicker(
            selectedTime = selectedTime,
            onTimeSelected = { selectedTime = it },
            label = "Hora de Cita"
        )
        
        // Botón de envío
        Button(
            onClick = {
                if (validateForm(name, birthDate, selectedTime)) {
                    submitForm(name, birthDate, selectedTime)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}

fun validateForm(name: String, date: String, time: String): Boolean {
    return name.isNotBlank() && date.isNotBlank() && time.isNotBlank()
}
```

### Ejemplo: Diálogo Personalizado

```kotlin
@Composable
fun CustomAlertExample() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    
    Button(
        onClick = { showDialog = true }
    ) {
        Text("Mostrar Calendario")
    }
    
    if (showDialog) {
        CalendarDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onDateSelected = { date ->
                selectedDate = date
                showDialog = false
            }
        )
    }
    
    selectedDate?.let { date ->
        Text(
            text = "Fecha seleccionada: ${DateUtils.formatDate(date, "dd/MM/yyyy")}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
```

### Ejemplo: Uso de Utilidades

```kotlin
@Composable
fun UtilitiesExample() {
    val currentDate = DateUtils.getCurrentDate()
    val formattedDate = DateUtils.formatDate(currentDate, "EEEE, dd MMMM yyyy")
    val dayOfWeek = DateUtils.getDayOfWeek(currentDate)
    
    var amount by remember { mutableStateOf(1234.56) }
    val formattedCurrency = NumberUtils.formatCurrency(amount)
    val formattedPercentage = NumberUtils.formatPercentage(0.1234)
    
    Column {
        Text("Fecha actual: $formattedDate")
        Text("Día de la semana: $dayOfWeek")
        Text("Cantidad: $formattedCurrency")
        Text("Porcentaje: $formattedPercentage")
        
        Button(
            onClick = {
                Vibrator.vibrate(200)
            }
        ) {
            Text("Vibrar")
        }
    }
}
```

## 🎨 Personalización

### Temas Personalizados

```kotlin
@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Colors.Primary,
            secondary = Colors.Secondary,
            background = Color(0xFF121212)
        )
    } else {
        lightColorScheme(
            primary = Colors.Primary,
            secondary = Colors.Secondary,
            background = Colors.Background
        )
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
```

## 🔧 Configuración Avanzada

### Localización

Los componentes soportan localización automática. Los textos se adaptan al idioma del sistema:

- Español
- Inglés
- Otros idiomas (próximamente)

### Accesibilidad

Todos los componentes incluyen soporte para accesibilidad:

- Descripciones semánticas
- Soporte para lectores de pantalla
- Navegación por teclado
- Contraste adecuado

## 🐛 Solución de Problemas

### Problemas Comunes

1. **Error de compilación con kotlinx-datetime**
   ```kotlin
   // Asegúrate de incluir la dependencia
   implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
   ```

2. **Componentes no se muestran correctamente**
   ```kotlin
   // Verifica que estés usando MaterialTheme
   MaterialTheme {
       YourContent()
   }
   ```

3. **Problemas de vibración en iOS**
   ```kotlin
   // La vibración requiere permisos específicos en iOS
   // Consulta la documentación de permisos
   ```

## 📚 Recursos Adicionales

- [Guía de Inicio Rápido](quick-start.md)
- [API Reference](api-reference.md)
- [Ejemplos Completos](../kmptestapp/)
- [Reportar Issues](https://github.com/erickvelasco11/KmpLibrary/issues)

---

**Próximas Características:**
- Más componentes de entrada
- Soporte para más idiomas
- Temas predefinidos adicionales
- Animaciones mejoradas