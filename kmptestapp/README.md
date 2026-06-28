[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android](https://img.shields.io/badge/Android-Platform-3DDC84.svg)](https://developer.android.com)
[![iOS](https://img.shields.io/badge/iOS-Platform-000000.svg)](https://developer.apple.com/ios/)

# KmpTestApp

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

KmpTestApp is the demonstration and test application module for the SweetMeSoft KMP Library project. It exercises and verifies all library modules (KmpBase, KmpControls, and KmpMaps) under a unified codebase. The app serves as a reference implementation for engineers to understand component integrations, theming setups, navigation flows, and state handling across target environments.

## Functionalities

- Input Controls Screen: Exercises the PasswordControl, SearchControl, and custom form validators.
- Date and Time Pickers Screen: Exercises DatePicker, TimePicker, and DateTimePicker inputs alongside their respective clock/calendar overlay dialogs.
- Alerts and Dialogue Screen: Showcases loading panels, confirmation popups, notifications, and progress dialogues managed via the global PopupHandler.
- Maps Screen: Exercises the MapComponent layout, showing dynamic pin placements, polyline rendering, circles, and user location tracking features.
- Dynamic Theme Showcase: Allows real-time transitions between light and dark visual themes, custom colors, and AMOLED modes.
- Profile photo loading and caching display: Pulls and renders images in circular layouts.

## Libraries and Dependencies

| Dependency | Category | Purpose |
|:---|:---|:---|
| KmpBase | Local Module | Integrates foundational screens, viewmodels, themes, and network helpers |
| KmpControls | Local Module | Integrates forms text fields and selection picker dialogs |
| KmpMaps | Local Module | Integrates Google Maps and MapKit map components |
| Voyager | Navigation | Manages the tab and transition navigation stack |
| Compose Material 3 | UI Components | Renders the primary material design guidelines |
| AndroidX Lifecycle | System | Couples viewmodel execution scopes with composable screens |

## Core Implementation

KmpTestApp uses common main resources to structure its views and logic. Navigation is powered by the Voyager navigation framework using a slide-transition engine. ViewModels extend the core BaseViewModel to run async tasks on a controlled dispatcher, pushing state flows back to compose components. Platform-specific entry points (MainActivity on Android and MainViewController on iOS) bootstrap the root compose screen.

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
kmptestapp
├── src
│   ├── androidMain  - Android bootstrap (MainActivity)
│   ├── commonMain   - Shared source sets and screens
│   │   ├── composeResources - Static assets (drawable)
│   │   └── kotlin/com/sweetmesoft/kmptestapp
│   │       ├── components   - Custom application widgets and viewcards
│   │       ├── screens      - Screen implementations and ViewModels
│   │       │   ├── about    - About screen layout
│   │       │   ├── controls - Password and search text field screen
│   │       │   ├── dialogs  - Global popups and alerts showcase screen
│   │       │   ├── main     - Menu interface screen
│   │       │   ├── map      - Native MapComponent layout screen
│   │       │   └── pickers  - Pickers and dialogue wrappers screen
│   │       └── tabs         - Bottom bar tab layout declarations
│   └── iosMain      - iOS UIViewController bootstrap
└── build.gradle.kts
```

## Design Patterns Implemented

- Model-View-ViewModel: Segregates screen structures from business logic ViewModels.
- State Hoisting: Elevates state variables from basic components to ViewModel scopes.
- Strategy Pattern: Employs different navigation approaches (drawers vs bottom navigation bars).
- Observer Pattern: Binds StateFlow properties to the UI layout.

## Configurations

Deploying the demonstration application requires setting up API credentials. Developers must configure the Google Maps API Key in the Android resource file values and configure location description permissions in the iOS Info.plist configuration. Build commands are executed using Gradle tasks to build and install the debug APK on Android devices or simulate Xcode frameworks on iOS configurations.

## Integrations

- Google Maps API: Displays local geographic details on Android systems.
- Apple MapKit Framework: Renders location maps on iOS platforms.
- Core Geolocation Services: Queries active device position properties.