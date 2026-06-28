[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android](https://img.shields.io/badge/Android-Platform-3DDC84.svg)](https://developer.android.com)
[![iOS](https://img.shields.io/badge/iOS-Platform-000000.svg)](https://developer.apple.com/ios/)
[![Gradle](https://img.shields.io/badge/Gradle-Build-02303A.svg)](https://gradle.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# SweetMeSoft KMP Library

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

SweetMeSoft KMP Library is a unified, multi-module Kotlin Multiplatform SDK designed to streamline cross-platform application development across Android, iOS, and Desktop platforms. The project provides basic UI components, forms inputs, native map visualizations, device-level location helpers, and standardized architectures for application screens and ViewModels. It abstracts platform-specific APIs and boilerplates into a common set of APIs.

## Functionalities

- Architecture Foundations: Implements standardized screen wrappers and lifecycle-aware view models for handling asynchronous operations.
- UI Controls: Provides custom interactive elements including password fields, search inputs, and standalone picker dialogs.
- Maps and Geolocation: Embeds native map views, manages overlay elements such as markers and polylines, and facilitates location acquisition services.
- Theme System: Generates color schemes dynamically utilizing base branding metrics conforming to Material Design specifications.
- Network Client Wrapper: Standardizes HTTP calls with support for dynamic loading states, headers, and authentication.
- Demonstration Application: Contains a showcase application for verifying and exercising all library modules on target platforms.

## Libraries and Dependencies

| Module / Dependency | Category | Purpose |
|:---|:---|:---|
| Compose Multiplatform | UI Framework | Native UI layout construction across target platforms |
| Kotlinx DateTime | Utility | Date and time calculations and conversions |
| Kotlinx Serialization | Utility | JSON serialization and deserialization |
| Voyager | Navigation | Navigation flow control, transitions, and state preservation |
| Material Kolor | Theme Utility | Color palette generation from primary key colors |
| Ktor Client | Network | Engine for standard HTTP transactions across Android and iOS |
| Moko Permissions | System Service | Unified system permission requests for location and device features |
| Play Services Location | System Service | Geolocation provider integration on Android devices |

## Core Implementation

The workspace compiles common Kotlin codebase into platform-specific targets. Android artifacts compile into Java Virtual Machine bytecode compatible with Dalvik/ART runtimes. iOS compilation utilizes the Kotlin/Native LLVM-based compiler to generate Apple Frameworks (xcframework binaries) consumed directly by Swift or Objective-C runtime environments. The application utilizes Compose Multiplatform to draw layouts directly onto native canvas targets, bypassing heavy native view hierarchies while maintaining natural platform responsiveness.

## Versions

- Kotlin Compiler: 2.4.0
- Compose Multiplatform: 1.11.1
- Android Compile SDK: 37
- Android Target SDK: 37
- Android Minimum SDK: 24
- iOS Deployment Target: 12.0 or higher
- Android Gradle Plugin: 9.2.1

## Folder Structure

```
.
├── kmpbase
│   └── src
│       └── commonMain
│           └── kotlin
│               └── com/sweetmesoft/kmpbase
│                   ├── base         - Screen model, viewModel, and navigation definitions
│                   ├── controls     - Custom lists, grids, dropdowns, and alert components
│                   ├── objects      - Data models and core platform structures
│                   ├── screens      - Pre-built splash and about screens
│                   ├── serializers  - Custom serialization formats
│                   ├── theme        - Color scheme and theme generation engines
│                   └── tools        - Platform utility wrappers and helper functions
├── kmpcontrols
│   └── src
│       └── commonMain
│           └── kotlin
│               └── com/sweetmesoft/kmpcontrols
│                   ├── controls     - Password and search text field controls
│                   ├── dialogs      - Date and time selection dialogs
│                   ├── pickers      - Text-input date and time picker wrappers
│                   └── tools        - Native vibration feedback tools
├── kmpmaps
│   └── src
│       └── commonMain
│           └── kotlin
│               └── com/sweetmesoft/kmpmaps
│                   └── controls     - Coordinates, geo-position, routes, and map components
└── kmptestapp
    └── src
        └── commonMain
            └── kotlin
                └── com/sweetmesoft/kmptestapp
                    ├── components   - Custom application widgets and viewcards
                    ├── screens      - Demonstration screens and ViewModels
                    └── tabs         - Navigation tab definitions
```

## Design Patterns Implemented

- Model-View-ViewModel: Architecture separating state declarations inside ViewModels from structural UI declarations in Compose screens.
- Adapter Pattern: Unified maps component wraps the platform-specific Google Maps API on Android and MapKit API on iOS behind a single common interface.
- Bridge Pattern: Declares platform-specific APIs inside the common main layer and connects them to actual implementations inside the androidMain and iosMain folders.
- Service Locator: Used within the navigation engine to lookup active screens, viewmodels, and navigator states.
- Observer Pattern: Employs Kotlin StateFlow and SharedFlow to push state updates asynchronously from ViewModels to Compose layouts.

## Configurations

The build configuration uses standard Kotlin Multiplatform Gradle configurations. Settings files define the modules in scope. The version catalog defines standard third-party library versions, plugins, and group names. Common source sets handle standard multiplatform business logic and layout scripts, while platform-specific source sets (androidMain, iosMain) configure platform-dependent libraries.

## Integrations

- Android Google Maps SDK: Integrates Android map layouts and native marker structures.
- Apple MapKit: Renders map layouts and overlays on iOS targets.
- Android Location Services: Accesses system location providers for GPS reading.
- iOS Core Location Framework: Hooks into iOS native location providers.
