---
layout: default
title: Home
nav_order: 1
---

# SweetMeSoft KMP Library

Welcome to the official documentation of SweetMeSoft KMP Library! A complete multiplatform library for Kotlin that allows you to create native applications for Android and iOS with a shared codebase.

## What is SweetMeSoft KMP Library?

SweetMeSoft KMP Library is a collection of specialized modules that provide:

- **Advanced UI components** for modern interfaces
- **Maps integration** multiplatform
- **Utilities and tools** for agile development
- **Practical examples** and best practices

## Available Modules

<div class="module-grid">
  <div class="module-card">
    <h3>KMPControls</h3>
    <p>Fundamental UI components like date pickers, dialogs, text fields and validation utilities.</p>
    <a href="kmpcontrols.html" class="btn">View Documentation</a>
  </div>
  
  <div class="module-card">
    <h3>KMPMaps</h3>
    <p>Complete maps integration with Google Maps for Android and MapKit for iOS, including markers and geolocation.</p>
    <a href="kmpmaps.html" class="btn">View Documentation</a>
  </div>
  
  <div class="module-card">
    <h3>Library</h3>
    <p>Main module with advanced components, navigation, network utilities, image handling and base architecture.</p>
    <a href="library.html" class="btn">View Documentation</a>
  </div>
  
  <div class="module-card">
    <h3>KMPTestApp</h3>
    <p>Example application that demonstrates the use of all modules with practical implementations and best practices.</p>
    <a href="kmptestapp.html" class="btn">View Documentation</a>
  </div>
</div>

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

### 2. Configure your Application

```kotlin
@Composable
fun App() {
    MaterialTheme {
        Navigator(screen = MainScreen()) { navigator ->
            BaseViewModel.navigator = navigator
            SlideTransition(navigator)
        }
    }
}
```

### 3. Use Components

```kotlin
@Composable
fun MyScreen() {
    var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
    var password by remember { mutableStateOf("") }
    
    Column {
        // Date picker from KMPControls
        DatePicker(
            value = selectedDate,
            title = "Select Date",
            onSelectedDate = { date ->
                selectedDate = date
            }
        )
        
        // Password control from KMPControls
        PasswordControl(
            value = password,
            label = "Password",
            onValueChange = { password = it }
        )
        
        // Map from KMPMaps
        MapComponent(
            modifier = Modifier.height(200.dp),
            coordinates = Coordinates(37.7749, -122.4194)
        )
    }
}
```

## Main Features

### True Multiplatform
- **Android** with Jetpack Compose
- **iOS** with Compose Multiplatform / Native
- Maximum shared code

### Modern Components
- Material Design 3
- Adaptive themes (light/dark)
- Smooth animations
- Built-in accessibility

### Native Maps
- Google Maps on Android
- MapKit on iOS
- Unified API
- Customizable markers

### Complete Utilities
- Form validation
- Image handling
- Network operations
- Advanced navigation

### Solid Architecture
- MVVM pattern
- StateFlow for reactive states
- Declarative navigation
- Centralized error handling

## Documentation Guides

<div class="guide-links">
  <a href="kmpcontrols.html" class="guide-link">
    <h4>KMPControls Guide</h4>
    <p>Learn to use selectors, dialogs and validations</p>
  </a>
  
  <a href="kmpmaps.html" class="guide-link">
    <h4>KMPMaps Guide</h4>
    <p>Implement native maps in your application</p>
  </a>
  
  <a href="library.html" class="guide-link">
    <h4>Library Guide</h4>
    <p>Master advanced components and utilities</p>
  </a>
  
  <a href="kmptestapp.html" class="guide-link">
    <h4>Practical Examples</h4>
    <p>See real implementations and best practices</p>
  </a>
</div>

## Example Projects

Explore our complete examples:

- **[KMPTestApp](kmptestapp.html)** - Complete demonstration application
- **[Advanced Forms](library.html)** - Validation and controls
- **[Maps Integration](kmpmaps.html)** - Geolocation and markers
- **[Complex Navigation](library.html)** - Navigation flows

## Community and Support

- **[Complete Documentation](https://github.com/erickvelasco11/KmpLibrary/blob/main/README.md)**
- **[Report Issues](https://github.com/erickvelasco11/KmpLibrary/issues)**
- **[Request Features](https://github.com/erickvelasco11/KmpLibrary/issues/new)**
- **[Contribute](https://github.com/erickvelasco11/KmpLibrary/blob/main/CONTRIBUTING.md)**

## Project Status

- **Current Version:** 2.0.1
- **Kotlin:** 2.2.21+
- **Compose:** 1.9.0+
- **Platforms:** Android, iOS
- **License:** MIT

---

<div class="footer-cta">
  <h3>Ready to get started?</h3>
  <p>Start with our installation guide and create your first multiplatform application.</p>
  <a href="https://github.com/erickvelasco11/KmpLibrary" class="btn btn-primary">View on GitHub</a>
  <a href="kmpcontrols.html" class="btn btn-secondary">Start Tutorial</a>
</div>

<style>
.module-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin: 30px 0;
}

.module-card {
  border: 1px solid #e1e4e8;
  border-radius: 8px;
  padding: 20px;
  background: #f8f9fa;
}

.module-card h3 {
  margin-top: 0;
  color: #0366d6;
}

.btn {
  display: inline-block;
  padding: 8px 16px;
  background: #0366d6;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  margin-top: 10px;
}

.btn:hover {
  background: #0256cc;
}

.btn-secondary {
  background: #6c757d;
}

.btn-secondary:hover {
  background: #5a6268;
}

.guide-links {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 15px;
  margin: 20px 0;
}

.guide-link {
  display: block;
  padding: 15px;
  border: 1px solid #e1e4e8;
  border-radius: 6px;
  text-decoration: none;
  color: inherit;
  transition: all 0.2s;
}

.guide-link:hover {
  border-color: #0366d6;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.guide-link h4 {
  margin: 0 0 8px 0;
  color: #0366d6;
}

.guide-link p {
  margin: 0;
  color: #586069;
  font-size: 14px;
}

.footer-cta {
  text-align: center;
  margin: 40px 0;
  padding: 30px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 8px;
}

.footer-cta h3 {
  margin-top: 0;
}

.footer-cta .btn {
  margin: 0 10px;
}
</style>
