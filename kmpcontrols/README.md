# Tutorial: Integrating SweetMeSoft KMPControls Library

Follow these steps to set up and use the SweetMeSoft KMPControls library in your project.

## 1. Download the Template

Start by downloading the template from [SweetMeSoft KMPControls](https://kmp.jetbrains.com/).

## 2. Build the Project

Use Gradle to build the project after importing the template.

## 3. Add Dependencies

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

## 4. Update Android SDK Versions

Set the minimum SDK version to 28 (Android 9 Pie). Update your project configuration as follows:

### `gradle.properties`

```properties
android-compileSdk="35"
android-minSdk="28"
android-targetSdk="35"
```

This configuration covers approximately 90% of devices worldwide, based
on [Android Version Market Share](https://gs.statcounter.com/android-version-market-share).

## 5. Add kotlinx-datetime Dependency

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