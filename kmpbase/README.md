[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android](https://img.shields.io/badge/Android-Platform-3DDC84.svg)](https://developer.android.com)
[![iOS](https://img.shields.io/badge/iOS-Platform-000000.svg)](https://developer.apple.com/ios/)

# KmpBase

## Table of Contents

- [Project Summary](#project-summary)
- [Functionalities](#functionalities)
- [Libraries and Dependencies](#libraries-and-dependencies)
- [Core Implementation](#core-implementation)
- [Versions](#versions)
- [Folder Structure](#folder-structure)
- [Design Patterns Implemented](#design-patterns-implemented)
- [Configurations](#configurations)
- [Integrations](#integrations)

## Project Summary

The KmpBase module represents the core architectural library for the SweetMeSoft KMP project. It provides base visual templates, view model abstractions, dynamic material theming utilities, a centralized popup dialog manager, HTTP client helpers, and localized platform tools. By wrapping low-level system permissions and network responses in a uniform interface, KmpBase serves as the foundation for the other UI and business logic submodules.

## Functionalities

- Base UI Screen Templates: Standardizes layout containers including basic screen types, navigation drawer interfaces, bottom tab navigation layouts, and modal bottom sheet controllers.
- Base Screen ViewModel: Manages coroutine scopes, processes permission requests, and integrates navigation commands directly into the screen lifecycle.
- Pre-built Screens: Deliver ready-to-use splash sequences and about application views.
- Dynamic Color Generation: Produces a fully custom Material Design 3 color palette given primary, secondary, or tonal style configuration variables.
- Popup and Alert Dispatcher: Broadcasters messages, confirmation inputs, prompt dialogs, and progress dialogs globally.
- Network Request Interceptors: Standardizes client GET, POST, PUT, PATCH, and DELETE calls while automatically managing loading states and security tokens.
- Platform Utilities: Interacts with host system properties, retrieves current app bundle details, and manages deep link browser navigation.

## Libraries and Dependencies

| Dependency | Purpose |
|:---|:---|
| Compose Multiplatform | Base drawing canvas, layout rendering, and material color system |
| Voyager | Unified navigation stack and screen model lifecycle handlers |
| Material Kolor | Generates dynamic tonal palettes and color schemes |
| Ktor Client | Multiplatform HTTP networking core and platform engines |
| Moko Permissions | System-level permission managers for location, camera, and gallery access |
| Kotlinx DateTime | Date and time manipulation engines |
| Kotlinx Serialization | Type-safe JSON serialization wrappers |

## Core Implementation

KmpBase builds a shared layer compile-compatible across Android and iOS environments. Networking relies on a shared Ktor client instance with platform-specific execution engines (OkHttp on Android, Darwin on iOS). Navigation hooks into the Voyager ScreenModel container to preserve states across device rotations and transitions. Custom alerts publish states to a central handler that triggers Jetpack Compose overlays.

## Versions

- Kotlin Version: 2.4.0
- Compose Multiplatform: 1.11.1
- Android Compile SDK: 37
- Android Target SDK: 37
- Android Minimum SDK: 24
- iOS Deployment Target: 12.0 or higher
- Android Gradle Plugin: 9.2.1

## Folder Structure

```
kmpbase
└── src
    └── commonMain
        └── kotlin
            └── com/sweetmesoft/kmpbase
                ├── base         - Screen models, BaseViewModel class, and navigation contracts
                ├── controls     - Custom List, Grid, Dropdown UI elements, and dialog templates
                ├── objects      - Shareable state representations and custom platform structures
                ├── screens      - Ready-made application views (SplashContent, AboutContent)
                ├── serializers  - Custom serial parsing algorithms
                ├── theme        - Theme wrappers and material-kolor scheme engines
                └── tools        - Platform property accessors, browser links, and string utils
```

## Design Patterns Implemented

- Model-View-ViewModel (MVVM): Imposed by coupling Voyager Screen models to Composable declarations.
- Singleton Pattern: Configured on the PopupHandler object to allow global dialog dispatching.
- Factory Pattern: Utilized in theme engines to build specialized color schemes based on branding styles.
- Adapter Pattern: Connects native device property APIs to common multiplatform utilities.
- Observable Pattern: Dispatches state flow outputs from ViewModels to keep Compose interfaces updated.

## Configurations

The core library module is configured via multi-module build dependencies. Consumers of the module specify KmpBase as an implementation dependency in their Gradle builds. System styles require an entry theme provider wrapper around root layouts.

## Integrations

- System Permission APIs: Bridges Android runtime permissions and iOS Info.plist declarations.
- Host Web Browser: Launches device-level Safari or Chrome pages via platform deep links.
- Remote Web APIs: Transacts with JSON REST endpoints through Ktor.
