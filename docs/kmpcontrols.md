# 📘 KMPControls Documentation

KMPControls is the fundamental module of the SweetMeSoft KMP library that provides basic UI components and essential utilities for multiplatform development.

## 📋 Table of Contents

- [Installation](#installation)
- [Available Components](#available-components)
- [Controls](#controls)
- [Dialogs](#dialogs)
- [Selectors](#selectors)
- [Utilities](#utilities)
- [Usage Examples](#usage-examples)

## 🚀 Installation

```kotlin
commonMain.dependencies {
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:1.6.6")
    
    // Required dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}
```

## 🎛️ Available Components

### Controls

#### ClickableOutlinedTextField
Text field that acts as a button, ideal for selectors and navigation.

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

**Usage example:**
```kotlin
ClickableOutlinedTextField(
    value = selectedDate,
    title = "Select Date",
    color = MaterialTheme.colorScheme.primary,
    onClick = { showDatePicker = true }
)
```

### 🗨️ Dialogs

#### BaseDialog
Customizable base dialog for creating consistent modals.

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
Dialog with integrated calendar for date selection.

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
Dialog for time selection with intuitive interface.

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
Standardized dialog footer with action buttons.

```kotlin
@Composable
fun DialogFooter(
    onCancel: () -> Unit,
    onAccept: () -> Unit,
    cancelText: String = "Cancel",
    acceptText: String = "Accept",
    acceptEnabled: Boolean = true
)
```

### 📅 Selectors (Pickers)

#### DatePicker
Date selector with Material Design interface.

```kotlin
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    label: String = "Date",
    format: String = "dd/MM/yyyy",
    enabled: Boolean = true
)
```

#### TimePicker
Time selector with customizable format.

```kotlin
@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    label: String = "Time",
    format: String = "HH:mm",
    enabled: Boolean = true
)
```

#### DateTimePicker
Combined date and time selector.

```kotlin
@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    selectedDateTime: String,
    onDateTimeSelected: (String) -> Unit,
    label: String = "Date and Time",
    dateFormat: String = "dd/MM/yyyy",
    timeFormat: String = "HH:mm",
    enabled: Boolean = true
)
```

### 🔧 Utilities

#### DateUtils
Utilities for date handling and formatting.

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
Utilities for number validation and formatting.

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
Utility for multiplatform haptic feedback.

```kotlin
object Vibrator {
    fun vibrate(duration: Long = 100)
    fun vibratePattern(pattern: LongArray)
    fun cancel()
}
```

### 🎨 Style Objects

#### Colors
Predefined color palette for the library.

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

## 📝 Usage Examples

### Complete Example: Form with Validation

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
        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Date selector
        DatePicker(
            selectedDate = birthDate,
            onDateSelected = { birthDate = it },
            label = "Birth Date"
        )
        
        // Time selector
        TimePicker(
            selectedTime = selectedTime,
            onTimeSelected = { selectedTime = it },
            label = "Appointment Time"
        )
        
        // Submit button
        Button(
            onClick = {
                if (validateForm(name, birthDate, selectedTime)) {
                    submitForm(name, birthDate, selectedTime)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

fun validateForm(name: String, date: String, time: String): Boolean {
    return name.isNotBlank() && date.isNotBlank() && time.isNotBlank()
}
```

### Example: Custom Dialog

```kotlin
@Composable
fun CustomAlertExample() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    
    Button(
        onClick = { showDialog = true }
    ) {
        Text("Show Calendar")
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
            text = "Selected date: ${DateUtils.formatDate(date, "dd/MM/yyyy")}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
```

### Example: Using Utilities

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
        Text("Current date: $formattedDate")
        Text("Day of week: $dayOfWeek")
        Text("Amount: $formattedCurrency")
        Text("Percentage: $formattedPercentage")
        
        Button(
            onClick = {
                Vibrator.vibrate(200)
            }
        ) {
            Text("Vibrate")
        }
    }
}
```

## 🎨 Customization

### Custom Themes

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

## 🔧 Advanced Configuration

### Localization

Components support automatic localization. Texts adapt to the system language:

- Spanish
- English
- Other languages (coming soon)

### Accessibility

All components include accessibility support:

- Semantic descriptions
- Screen reader support
- Keyboard navigation
- Adequate contrast

## 🐛 Troubleshooting

### Common Issues

1. **Compilation error with kotlinx-datetime**
   ```kotlin
   // Make sure to include the dependency
   implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
   ```

2. **Components not displaying correctly**
   ```kotlin
   // Verify that you're using MaterialTheme
   MaterialTheme {
       YourContent()
   }
   ```

3. **Vibration issues on iOS**
   ```kotlin
   // Vibration requires specific permissions on iOS
   // Check the permissions documentation
   ```

## 📚 Additional Resources

- [Quick Start Guide](quick-start.md)
- [API Reference](api-reference.md)
- [Complete Examples](../kmptestapp/)
- [Report Issues](https://github.com/erickvelasco11/KmpLibrary/issues)

---

**Upcoming Features:**
- More input components
- Support for more languages
- Additional predefined themes
- Enhanced animations