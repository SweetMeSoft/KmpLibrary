# SweetMeSoft KMP Library

A complete cross-platform library for Kotlin that provides advanced UI components, maps integration, and essential utilities for developing native applications on Android, iOS, and Desktop.

[![GitHub release](https://img.shields.io/github/release/SweetMeSoft/KmpLibrary.svg)](https://github.com/SweetMeSoft/KmpLibrary/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.7.0%2B-green.svg)](https://developer.android.com/jetpack/compose)
[![Documentation](https://img.shields.io/badge/docs-GitHub%20Pages-blue)](https://sweetmesoft.github.io/KmpLibrary)

## Table of Contents

- [Features](#features)
- [Modules](#modules)
- [Quick Start](#quick-start)
- [Documentation](#documentation)
- [Installation](#installation)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)
- [Authors](#authors)
- [Links](#links)

## Features

- **Modern UI Components**: Custom controls with Material Design 3
- **Maps Integration**: Cross-platform map components with Google Maps
- **Complete KMP Support**: Compatible with Android and iOS
- **Integrated Utilities**: Tools for dates, numbers, images, and more
- **Easy Integration**: Simple and well-documented APIs
- **High Performance**: Optimized for production applications

## Modules

The library is divided into three main modules:

### KMPControls
Basic UI components and fundamental utilities.
- Custom input controls
- Date and time pickers
- Dialogs and alerts
- Validation utilities

### KMPMaps
Specialized components for maps integration.
- Cross-platform map components
- Custom markers and overlays
- Location management
- Google Maps integration

### Library (Main)
Main library with advanced components and tools.
- Complex UI components
- State management
- Network utilities
- Navigation tools

### KMPTestApp
Example application that demonstrates the use of all libraries.

## Installation

### Gradle (Kotlin DSL)

Add the dependencies in your `build.gradle.kts` file:

```kotlin
commonMain.dependencies {
    // Main library
    implementation("com.sweetmesoft.kmpbase:kmpbase:2.0.1")
    
    // Basic controls
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:2.0.1")
    
    // Map components
    implementation("com.sweetmesoft.kmpmaps:kmpmaps:2.0.1")
}
```

### Version Configuration

In your `libs.versions.toml` file:

```toml
[versions]
sweetmesoft = "2.0.1"

[libraries]
sweetmesoft-library = { module = "com.sweetmesoft.kmpbase:kmpbase", version.ref = "sweetmesoft" }
sweetmesoft-kmpcontrols = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
sweetmesoft-kmpmaps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }
```

## Requirements

- **Kotlin**: 2.1.0+
- **Compose Multiplatform**: 1.7.0+
- **Android**: API 28+ (Android 9.0)
- **iOS**: iOS 12.0+
- **Gradle**: 8.0.0+

### Required Dependencies

```kotlin
// In your build.gradle.kts
commonMain.dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("dev.icerock.moko:permissions:0.18.0")
    implementation("dev.icerock.moko:permissions-compose:0.18.0")
}
```

## Quick Start

### 1. Add Dependencies

In your `libs.versions.toml` file:

```toml
[versions]
sweetmesoft = "2.0.1"

[libraries]
sweetmesoft-kmpcontrols = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
sweetmesoft-kmpmaps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }
sweetmesoft-library = { module = "com.sweetmesoft:library", version.ref = "sweetmesoft" }
```

In your `build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation(libs.sweetmesoft.kmpcontrols)
    implementation(libs.sweetmesoft.kmpmaps)
    implementation(libs.sweetmesoft.library)
}
```

### 2. Use Components

```kotlin
import androidx.compose.runtime.*
import com.sweetmesoft.kmpcontrols.pickers.DatePicker
import com.sweetmesoft.kmpbase.controls.PasswordControl
import com.sweetmesoft.kmpmaps.MapComponent
import com.sweetmesoft.kmpmaps.controls.Coordinates

@Composable
fun MyApp() {
    var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
    var password by remember { mutableStateOf("") }
    
    Column {
        // Password control
        PasswordControl(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )
        
        // Date picker
        DatePicker(
            value = selectedDate,
            title = "Select Date",
            onSelectedDate = { selectedDate = it }
        )
        
        // Map component
        MapComponent(
            modifier = Modifier.fillMaxSize(),
            coordinates = Coordinates(40.7128, -74.0060)
        )
    }
}
```

## Documentation

### Complete Documentation

**[Visit our complete documentation on GitHub Pages](https://sweetmesoft.github.io/KmpLibrary)**

The documentation includes:

- **Quick start guides** for each module
- **Detailed API** with code examples
- **Customization guides** and themes
- **Practical examples** and use cases
- **Advanced configuration** for each platform
- **Common troubleshooting** solutions

### Documentation by Module

| Module | Description | Documentation |
|--------|-------------|---------------|
| **KMPControls** | Basic UI components and utilities | [View Docs](https://sweetmesoft.github.io/KmpLibrary/kmpcontrols) |
| **KMPMaps** | Cross-platform maps integration | [View Docs](https://sweetmesoft.github.io/KmpLibrary/kmpmaps) |
| **Library** | Advanced components and tools | [View Docs](https://sweetmesoft.github.io/KmpLibrary/library) |
| **KMPTestApp** | Example application and guides | [View Docs](https://sweetmesoft.github.io/KmpLibrary/kmptestapp) |

### Local Documentation

You can also access the documentation locally:

- **[KMPControls](docs/kmpcontrols.md)** - Basic UI components
- **[KMPMaps](docs/kmpmaps.md)** - Maps integration
- **[Library](docs/library.md)** - Main module
- **[KMPTestApp](docs/kmptestapp.md)** - Usage examples

## Project Configuration

### Android

In your Android `build.gradle.kts`:

```kotlin
android {
    compileSdk = 36
    defaultConfig {
        minSdk = 28
        targetSdk = 36
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
```

### iOS

Make sure you have the minimum deployment target configured:

```kotlin
iosX64()
iosArm64()
iosSimulatorArm64()
```

## Customization

### Custom Themes

```kotlin
@Composable
fun MyCustomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6)
        )
    ) {
        content()
    }
}
```

## Examples

The project includes a complete example application in the `kmptestapp` module that demonstrates:

- Use of all UI components
- Maps integration
- Navigation between screens
- State management
- Development best practices

To run the example application:

```bash
./gradlew :kmptestapp:run
```

## Contributing

Contributions are welcome! We have a complete guide for contributors.

**[Read our Contributing Guide](CONTRIBUTING.md)**

### Ways to Contribute

- **Report bugs** - Help us improve by reporting issues
- **Suggest features** - Share your ideas for new functionalities
- **Improve documentation** - Help make documentation clearer
- **Contribute code** - Implement new features or fix bugs
- **Write tests** - Improve test coverage
- **Translate** - Help make the library more accessible

### Quick Start for Contributors

```bash
# 1. Fork and clone the repository
git clone https://github.com/YOUR_USERNAME/KmpLibrary.git
cd KmpLibrary

# 2. Create a branch for your feature
git checkout -b feature/my-new-feature

# 3. Install dependencies and build
./gradlew build

# 4. Run tests
./gradlew test

# 5. Test the example app
./gradlew :kmptestapp:installDebug
```

### Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to maintain a respectful and welcoming environment for everyone.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Authors

- **Erick Velasco** - *Main Development* - [erick.velasco@sweetmesoft.com](mailto:erick.velasco@sweetmesoft.com)

## Links

### Documentation and Resources
- **[Complete Documentation](https://sweetmesoft.github.io/KmpLibrary)** - GitHub Pages
- **[Contributing Guide](CONTRIBUTING.md)** - How to contribute to the project
- **[Example Application](kmptestapp/)** - Example source code
- **[Changelog](https://github.com/SweetMeSoft/KmpLibrary/releases)** - Change history

### Development
- **[Main Repository](https://github.com/SweetMeSoft/KmpLibrary)** - Source code
- **[Issues and Bug Reports](https://github.com/SweetMeSoft/KmpLibrary/issues)** - Report problems
- **[Releases](https://github.com/SweetMeSoft/KmpLibrary/releases)** - Published versions
- **[Maven Central](https://search.maven.org/search?q=g:com.sweetmesoft)** - Published packages

### Community
- **[Discussions](https://github.com/SweetMeSoft/KmpLibrary/discussions)** - Questions and discussions
- **[Contact](mailto:team@sweetmesoft.com)** - Direct contact
- **[Twitter](https://twitter.com/sweetmesoft)** - Updates and news

## Project Status

| Aspect | Status |
|---------|--------|
| **Current Version** | ![Version](https://img.shields.io/badge/version-2.0.1-blue) |
| **Status** | ![Status](https://img.shields.io/badge/status-Active%20development-green) |
| **Kotlin** | ![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-purple) |
| **Compose** | ![Compose](https://img.shields.io/badge/Compose-1.7.0%2B-orange) |
| **Platforms** | ![Platforms](https://img.shields.io/badge/platforms-Android%20%7C%20iOS%20%7C%20Desktop-lightgrey) |
| **License** | ![License](https://img.shields.io/badge/license-MIT-yellow) |
| **Tests** | ![Tests](https://img.shields.io/badge/tests-passing-brightgreen) |
| **Documentation** | ![Docs](https://img.shields.io/badge/docs-complete-blue) |

### Roadmap

- **v1.6.x** - Basic components and maps (Completed)
- **v1.7.x** - Performance improvements and new components (In Progress)
- **v1.8.x** - Support for more platforms (Planned)
- **v2.0.x** - Renewed API and advanced features (Future)

---

<div align="center">

**Do you like SweetMeSoft KMP Library?**

**[Give it a star on GitHub](https://github.com/SweetMeSoft/KmpLibrary)** • **[Explore Documentation](https://sweetmesoft.github.io/KmpLibrary)** • **[Get Started Now](#quick-start)** • **[Contribute](CONTRIBUTING.md)**

</div>

---

**Need help?** Open an [issue](https://github.com/SweetMeSoft/KmpLibrary/issues) or contact the development team.
