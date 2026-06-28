[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Android](https://img.shields.io/badge/Android-Platform-3DDC84.svg)](https://developer.android.com)
[![iOS](https://img.shields.io/badge/iOS-Platform-000000.svg)](https://developer.apple.com/ios/)

# KmpControls

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

KmpControls is a Kotlin Multiplatform library focused on providing advanced Date, Time, and DateTime pickers, custom dialog dialogs, and essential input controls. The module ensures a natural, platform-specific input flow across Android and iOS by complying with Material Design 3 guidelines and integrating platform haptic vibration mechanics.

## Functionalities

- Date Selection Picker: Text input layout displaying calendar views for simple day selections.
- Time Selection Picker: Text input layout displaying clock selection graphics.
- DateTime Sequence Picker: Chained picker flow presenting a calendar dialog immediately followed by a clock dialog.
- Calendar Overlay Dialog: Standalone modal component rendering month grid views.
- Clock Overlay Dialog: Standalone modal component rendering interactive clock dial interfaces.
- Password Control: Specialized text field rendering input characters masked, featuring a secure visibility toggling action.
- Search Control: High-visibility search field with clear triggers and custom action callbacks.
- Haptic Vibration Feedback: Emits micro-vibration cues during picker rotations and selection confirmations.

## Libraries and Dependencies

| Dependency | Category | Purpose |
|:---|:---|:---|
| Compose Multiplatform | UI Design | Implements Material Design 3 inputs, text fields, and overlays |
| Kotlinx DateTime | Date Logic | Models, formats, and validates calendar dates and times |
| Android Activity Compose | Platform UI | Integrates application context with Compose on Android devices |

## Core Implementation

KmpControls builds on top of the shared Compose Multiplatform canvas. Pickers are designed as wrapper elements around standard OutlinedTextField controls. When users focus or tap the target area, the module intercepts the event and pops open custom modal dialog surfaces. Haptic responses run on a common adapter interface, directing calls to Android-specific vibration services or iOS UIKit haptic engines.

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
kmpcontrols
└── src
    └── commonMain
        └── kotlin
            └── com/sweetmesoft/kmpcontrols
                ├── controls  - Input text layouts (PasswordControl, SearchControl)
                ├── dialogs   - Overlay dialogue layers (CalendarDialog, ClockDialog)
                ├── pickers   - Combination field-picker widgets (DatePicker, TimePicker)
                └── tools     - Common vibration service abstractions
```

## Design Patterns Implemented

- Compound Component Pattern: Combines text fields, icons, and dialog overlays into cohesive UI units.
- Adapter Pattern: Wraps the Android Vibrator API and iOS UIFeedbackGenerator behind a single platform-independent vibration interface.
- State Hoisting: Delegates picker selections up to owner states via selection callback triggers.

## Configurations

Using KmpControls requires initializing the Android helper utility inside the Android host MainActivity. This setup connects the shared library to the application context necessary for accessing system vibrator resources. Android applications must also declare haptic vibration permissions in their target manifest settings.

## Integrations

- Android System Vibrator: Translates picker scroll gestures into physical device haptics.
- iOS UIKit Haptic Feedback: Operates UIKit-level micro-vibrations on Apple devices.
- System Calendar Provider: Uses system locale defaults to structure month matrices and date-time layouts.
