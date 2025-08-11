# KMPControls - Advanced Date and Time Pickers

KMPControls is a Kotlin Multiplatform library that provides advanced date and time picker components with native look and feel across Android and iOS platforms.

## Features

- Cross-platform date, time, and datetime pickers
- Native UI components with platform-specific styling
- Customizable appearance and behavior
- Dialog and inline picker modes
- Comprehensive date/time utilities
- Full integration with kotlinx-datetime
- Material Design 3 support
- Accessibility features

## Installation

### 1. Add Dependencies

Update your `libs.versions.toml` and `build.gradle.kts` files with the required dependencies:

### `libs.versions.toml`

```toml
[versions]
sweetmesoft = "{version-sweetmesoft}"

[libraries]
sweetmesoft-kmpcontrols = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
```

### `build.gradle.kts`

```kotlin
implementation(libs.sweetmesoft.kmpcontrols)
```

### 2. Platform Requirements

#### Android
- Minimum SDK: 28 (Android 9 Pie)
- Target SDK: 35
- Compile SDK: 35

Update your `gradle.properties`:

```properties
android-compileSdk="35"
android-minSdk="28"
android-targetSdk="35"
```

#### iOS
- iOS 12.0+
- Xcode 14.0+

### 3. Required Dependencies

This library requires the official `kotlinx-datetime` library. Add the following dependency:

### `libs.versions.toml`

```toml
[versions]
kotlinx-datetime = "{version-datetime}"

[libraries]
kotlinx-datetime = { module = "org.jetbrains.kotlinx:kotlinx-datetime", version.ref = "kotlinx-datetime" }
```

### `build.gradle.kts`

```kotlin
implementation(libs.kotlinx.datetime)
```

For more details, visit
the [kotlinx-datetime GitHub page](https://github.com/Kotlin/kotlinx-datetime).

## Usage Examples

### Basic Date Picker

```kotlin
import kotlinx.datetime.LocalDate
import com.sweetmesoft.kmpcontrols.DatePicker

@Composable
fun DatePickerExample() {
    var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
    
    DatePicker(
        selectedDate = selectedDate,
        onDateSelected = { date ->
            selectedDate = date
        }
    )
}
```

### Time Picker

```kotlin
import kotlinx.datetime.LocalTime
import com.sweetmesoft.kmpcontrols.TimePicker

@Composable
fun TimePickerExample() {
    var selectedTime by remember { mutableStateOf(LocalTime(12, 0)) }
    
    TimePicker(
        selectedTime = selectedTime,
        onTimeSelected = { time ->
            selectedTime = time
        }
    )
}
```

### DateTime Picker

```kotlin
import kotlinx.datetime.LocalDateTime
import com.sweetmesoft.kmpcontrols.DateTimePicker

@Composable
fun DateTimePickerExample() {
    var selectedDateTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) }
    
    DateTimePicker(
        selectedDateTime = selectedDateTime,
        onDateTimeSelected = { dateTime ->
            selectedDateTime = dateTime
        }
    )
}
```

### Calendar Dialog

```kotlin
import com.sweetmesoft.kmpcontrols.CalendarDialog

@Composable
fun CalendarDialogExample() {
    var showCalendarDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
    
    Button(
        onClick = { showCalendarDialog = true }
    ) {
        Text("Select Date")
    }
    
    CalendarDialog(
        showDialog = showCalendarDialog,
        selectedDate = selectedDate,
        onDateSelected = { date ->
            selectedDate = date
            showCalendarDialog = false
        },
        onDismiss = {
            showCalendarDialog = false
        }
    )
}
```

### Clock Dialog

```kotlin
import com.sweetmesoft.kmpcontrols.ClockDialog

@Composable
fun ClockDialogExample() {
    var showClockDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime(12, 0)) }
    
    Button(
        onClick = { showClockDialog = true }
    ) {
        Text("Select Time")
    }
    
    ClockDialog(
        showDialog = showClockDialog,
        selectedTime = selectedTime,
        onTimeSelected = { time ->
            selectedTime = time
            showClockDialog = false
        },
        onDismiss = {
            showClockDialog = false
        }
    )
}
```

## Advanced Usage

### Custom Styling

```kotlin
DatePicker(
    selectedDate = selectedDate,
    onDateSelected = { selectedDate = it },
    colors = DatePickerColors(
        primaryColor = Color.Blue,
        backgroundColor = Color.White,
        textColor = Color.Black
    ),
    dateFormat = "dd/MM/yyyy"
)
```

### Date Range Restrictions

```kotlin
val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
val minDate = today.minus(30, DateTimeUnit.DAY)
val maxDate = today.plus(30, DateTimeUnit.DAY)

DatePicker(
    selectedDate = selectedDate,
    onDateSelected = { selectedDate = it },
    minDate = minDate,
    maxDate = maxDate
)
```

## API Reference

### DatePicker
- `selectedDate: LocalDate` - Currently selected date
- `onDateSelected: (LocalDate) -> Unit` - Callback when date is selected
- `minDate: LocalDate?` - Minimum selectable date
- `maxDate: LocalDate?` - Maximum selectable date
- `colors: DatePickerColors?` - Custom color scheme
- `dateFormat: String?` - Custom date format

### TimePicker
- `selectedTime: LocalTime` - Currently selected time
- `onTimeSelected: (LocalTime) -> Unit` - Callback when time is selected
- `is24Hour: Boolean` - Use 24-hour format (default: true)
- `colors: TimePickerColors?` - Custom color scheme

### DateTimePicker
- `selectedDateTime: LocalDateTime` - Currently selected date and time
- `onDateTimeSelected: (LocalDateTime) -> Unit` - Callback when datetime is selected
- `minDateTime: LocalDateTime?` - Minimum selectable datetime
- `maxDateTime: LocalDateTime?` - Maximum selectable datetime

## Requirements

- Kotlin Multiplatform 1.9.0+
- Compose Multiplatform 1.5.0+
- kotlinx-datetime 0.6.1+
- Android API 28+ / iOS 12.0+

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please contact SweetMeSoft or create an issue in the project repository.

## 6. Use the Composables

You can now use the `DatePicker`, `TimePicker`, and `DateTimePicker` components in your UI. Here’s
an example:

### Example

```kotlin
var date: LocalDate by remember { mutableStateOf(getCurrentDateTime().date) }
var time: LocalTime by remember { mutableStateOf(getCurrentDateTime().time) }
var dateTime: LocalDateTime by remember { mutableStateOf(getCurrentDateTime()) }

Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    DatePicker(value = date) { selectedDate ->
        date = selectedDate
    }
    TimePicker(value = time) { selectedTime ->
        time = selectedTime
    }
    DateTimePicker(value = dateTime) { selectedDateTime ->
        dateTime = selectedDateTime
    }
}
```

## 7. Show Dialogs for Date and Time Pickers

If you want to use dialogs for selecting dates or times, use `CalendarDialog` or `ClockDialog` as
shown below:

### Example

```kotlin
var showDatePicker by remember { mutableStateOf(false) }
var showTimePicker by remember { mutableStateOf(false) }

Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    Button(
        modifier = Modifier.weight(1f),
        content = { Text("Show Date") },
        onClick = { showDatePicker = true }
    )
    Button(
        modifier = Modifier.weight(1f),
        content = { Text("Show Time") },
        onClick = { showTimePicker = true }
    )
}

CalendarDialog(
    isVisible = showDatePicker,
    value = date,
    onDismiss = { showDatePicker = false }
) { selectedDate ->
    date = selectedDate
    showDatePicker = false
}

ClockDialog(
    isVisible = showTimePicker,
    value = time,
    onDismiss = { showTimePicker = false }
) { selectedTime ->
    time = selectedTime
    showTimePicker = false
}
```

## Contributing

We welcome contributions to KMPControls! Please feel free to submit issues, feature requests, or pull requests.

## Changelog

See the [CHANGELOG.md](CHANGELOG.md) file for details about changes in each version.