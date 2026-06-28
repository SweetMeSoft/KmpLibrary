[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android](https://img.shields.io/badge/Android-Platform-3DDC84.svg)](https://developer.android.com)
[![iOS](https://img.shields.io/badge/iOS-Platform-000000.svg)](https://developer.apple.com/ios/)

# KmpMaps

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

KmpMaps is a Kotlin Multiplatform library that establishes a unified MapComponent container. By encapsulating platform-specific map providers (Google Maps SDK on Android and MapKit on iOS), the module offers a single, composable API for rendering map canvases, tracking positions, drawing routes, and overlays.

## Functionalities

- Unified Map View: Renders maps across target systems, maintaining natural scroll, zoom, rotation, and gesture responsiveness.
- Marker Manager: Renders pins on the map with customizable descriptions, titles, and boundary colors.
- Route Polyline Drawer: Connects list arrays of coordinate points into paths, displaying custom route lines on the canvas.
- Circle Overlays: Draws geometric circles centered at target coordinates with adjustable boundary stroke and background fill parameters.
- User Geolocation Service: Displays user location indicators and retrieves active latitude/longitude details.
- Navigation Helper Utilities: Calculates geographic distances between points and computes center-zoom layouts automatically.

## Libraries and Dependencies

| Dependency | Category | Purpose |
|:---|:---|:---|
| Compose Multiplatform | UI Canvas | Renders the map container within active layouts |
| Google Maps Compose | Android Map Provider | Powers Google Maps rendering on Android platforms |
| Play Services Location | Geolocation Provider | Retrieves GPS data on Android devices |
| Moko Permissions | System Permissions | Manages runtime GPS permissions across platforms |

## Core Implementation

The KmpMaps module uses standard expect/actual constructs to abstract native platform maps. On Android, the module instantiates a standard Android Compose MapView wrapping the Google Play Services maps engine. On iOS, the module uses a UIViewControllerRepresentable component to embed an Apple MapKit MKMapView. Geolocation coordinates are modeled as common data objects and passed directly down to the platform adapter levels.

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
kmpmaps
└── src
    └── commonMain
        └── kotlin
            └── com/sweetmesoft/kmpmaps
                └── controls - Map component definitions, Routes, Circles, and Coordinates
```

## Design Patterns Implemented

- Adapter Pattern: Wraps the distinct iOS MapKit API and Android Google Maps API under a single common MapComponent API.
- Bridge Pattern: Connects common declarations of the map canvas to platform-specific views.
- Observer Pattern: Listens to location changes to reposition map views dynamically.

## Configurations

Deploying maps functionality requires platform-specific credential configurations. Developers must declare a Google Maps API Key in their Android project properties or manifest files and register location permission keys in the Android manifest structure. For iOS, developers must configure location description keys in their Info.plist file to justify background and foreground location retrieval.

## Integrations

- Google Maps API: Integrates Android map layouts and marker assets.
- Apple MapKit: Renders map visuals on iOS systems.
- Android Location Providers: Hooks into Google Play Services for location information.
- iOS Core Location Manager: Requests location telemetry from Apple devices.
