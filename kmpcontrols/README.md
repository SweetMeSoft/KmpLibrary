# KMPControls

KMPControls is a Kotlin Multiplatform library that provides advanced date and time picker components, along with other useful UI controls, offering a native look and feel across Android and iOS platforms.

## Features

- **Cross-platform Pickers**: Fully functional Date, Time, and DateTime pickers.
- **Dialogs**: Customizable standalone Calendar and Clock dialogs.
- **Additional Controls**:
  - `PasswordControl`: Password input with visibility toggle.
  - `SearchControl`: Search bar component.
- **Material Design 3**: Built using Jetpack Compose Material 3.
- **Kotlin Multiplatform**: Seamless support for Android and iOS.

## Installation

Add the dependency to your project.

### Using Version Catalog

If you are using a version catalog (e.g., `libs.versions.toml`), add the following:

```toml
[versions]
sweetmesoft = "1.7.7"

[libraries]
sweetmesoft-kmpcontrols = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
```

### Build Gradle

In your `build.gradle.kts` (commonMain source set):

```kotlin
implementation(libs.sweetmesoft.kmpcontrols)
```

## Android Initialization

For Android, you need to initialize the library in your `MainActivity` to support features like vibration feedback.

```kotlin
// In your Android MainActivity.kt
import com.sweetmesoft.kmpcontrols.tools.BaseAndroid

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize KMPControls
        BaseAndroid.initSweetMeSoft(this)
        
        setContent {
            // Your content
        }
    }
}
```

### Permissions (Android)

For haptic feedback (vibration) in pickers, add the following permission to your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.VIBRATE" />
```

## Usage

### DatePicker

A text field that opens a calendar dialog when clicked.

```kotlin
import com.sweetmesoft.kmpcontrols.pickers.DatePicker
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

// ... inside your Composable
var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }

DatePicker(
    value = selectedDate,
    title = "Select Date",
    onSelectedDate = { date ->
        selectedDate = date
    }
)
```

### TimePicker

A text field that opens a clock dialog when clicked.

```kotlin
import com.sweetmesoft.kmpcontrols.pickers.TimePicker

var selectedTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time) }

TimePicker(
    value = selectedTime,
    title = "Select Time",
    onSelectedTime = { time ->
        selectedTime = time
    }
)
```

### DateTimePicker

A text field that opens a calendar dialog followed by a clock dialog.

```kotlin
import com.sweetmesoft.kmpcontrols.pickers.DateTimePicker

var selectedDateTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) }

DateTimePicker(
    value = selectedDateTime,
    title = "Select Date & Time",
    onSelectedDateTime = { dateTime ->
        selectedDateTime = dateTime
    }
)
```

### CalendarDialog

Use the calendar dialog directly.

```kotlin
import com.sweetmesoft.kmpcontrols.dialogs.CalendarDialog

CalendarDialog(
    isVisible = showCalendar,
    value = selectedDate,
    onDismiss = { showCalendar = false },
    onDateSelected = { date ->
        selectedDate = date
        showCalendar = false
    }
)
```

### ClockDialog

Use the clock dialog directly.

```kotlin
import com.sweetmesoft.kmpcontrols.dialogs.ClockDialog

ClockDialog(
    isVisible = showClock,
    value = selectedTime,
    onDismiss = { showClock = false },
    onTimeSelected = { time ->
        selectedTime = time
        showClock = false
    }
)
```

### PasswordControl

A password input field with a visibility toggle icon.

```kotlin
import com.sweetmesoft.kmpcontrols.controls.PasswordControl

var password by remember { mutableStateOf("") }

PasswordControl(
    value = password,
    label = "Password",
    onValueChange = { password = it }
)
```

### SearchControl

A search bar component.

```kotlin
import com.sweetmesoft.kmpcontrols.controls.SearchControl

var query by remember { mutableStateOf("") }

SearchControl(
    value = query,
    placeholder = "Search...",
    onValueChange = { query = it },
    onSearch = { 
        // Perform search
    }
)
```

## API Reference

### Pickers

**DatePicker**
- `value`: `LocalDate` - The currently selected date.
- `onSelectedDate`: `(LocalDate) -> Unit` - Callback when a date is selected.
- `title`: `String` - Label for the text field.
- `minDate`, `maxDate`: `LocalDate` - Date range constraints.
- `color`: `Color` - Primary color for the picker.

**TimePicker**
- `value`: `LocalTime` - The currently selected time.
- `onSelectedTime`: `(LocalTime) -> Unit` - Callback when a time is selected.
- `title`: `String` - Label for the text field.

**DateTimePicker**
- `value`: `LocalDateTime` - The currently selected date and time.
- `onSelectedDateTime`: `(LocalDateTime) -> Unit` - Callback when date and time are selected.

### Controls

**PasswordControl**
- `value`: `String` - Current password text.
- `onValueChange`: `(String) -> Unit` - Callback for text changes.
- `label`: `String` - Label text.
- `isError`: `Boolean` - Whether to show error state.

**SearchControl**
- `value`: `String` - Current search query.
- `onValueChange`: `(String) -> Unit` - Callback for text changes.
- `onSearch`: `(String) -> Unit` - Callback when search action is triggered (e.g., keyboard enter).

## Requirements

- **Android**: Min SDK 28, Target SDK 36
- **iOS**: 12.0+
- **Kotlin**: 2.2.21+
- **Compose Multiplatform**: 1.9.0+

## License

This project is licensed under the MIT License.
