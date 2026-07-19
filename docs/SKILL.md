---
name: kmp-library
description: Guidelines and reference documentation for the SweetMeSoft KMP Library (KMPBase, KMPControls, and KMPMaps) in the Kotlin Multiplatform mobile application.
---

# KMP Library Usage Skill

This document provides the high-level capabilities of the SweetMeSoft KMP Library modules (KMPBase, KMPControls, and KMPMaps) and serves as the central directory mapping to detailed guides and references in the same folder.

---

## 1. Core Project Architecture Rules

To maintain architectural consistency across projects using this library, you must follow these rules:

1.  **Navigation**: Use Voyager for navigation (`Navigator`). Set `BaseViewModel.navigator = navigator` to enable ViewModel-based routing.
2.  **Theming**: Wrap the main application entry Composable inside `AppTheme` to apply unified design colors and shapes.
3.  **Screens Layout**: All screens must inherit from one of the core screen wrappers: `BaseScreen` (standard pages), `BaseBottomBarScreen` (tab layouts), `BaseDrawerScreen` (drawer layouts), or `BaseBottomSheetScreen` (bottom sheets).
4.  **Business Logic**: All ViewModels must inherit from `BaseViewModel` to utilize integrated permissions handlers and global loading state bindings.
5.  **Android Initialization**: Initialize the library in Android `MainActivity` inside `onCreate` by calling `initSweetMeSoft(this)` before setting Compose content.
6.  **Network Calls**: All HTTP requests must use `NetworkUtils` (`get` / `post` / etc) to support global progress overlays and status dialog bindings.

---

## 2. Directory of Reference Manuals

Use the links below to access the step-by-step guides on how to install, set up, and implement features using this library:

*   **Getting Started**: [getting-started.md](getting-started.md) — Installation, version catalogs, dependencies configuration, and initialization routines.
*   **KMPBase Guide**: [kmpbase.md](kmpbase.md) — Core screen wrappers, view models, lists, network utilities, alerts, and platform serializers.
*   **KMPControls Guide**: [kmpcontrols.md](kmpcontrols.md) — Date/time pickers, password and query inputs, autocomplete text fields, dialog overlays, and haptic feedback.
*   **KMPMaps Guide**: [kmpmaps.md](kmpmaps.md) — Native maps components integration, custom markers, routing lines, and geography utilities.

---

## 3. Capability Breakdown by Library Module

### A. KMPBase Core Module

Provides the foundational architecture, standard page frames, alerts, list adapters, and native platform helpers.

*   **Base Layout Frames**:
    *   `BaseScreen`: Primary page wrapper with built-in toolbar, loading states, dialog hooks, and FAB binds.
    *   `BaseBottomBarScreen`: Screen scaffolding managing tab navigation bars.
    *   `BaseDrawerScreen`: Screen scaffolding managing collapsible side navigation drawers.
    *   `BaseBottomSheetScreen`: Wrapper layout displaying screen views inside overlay bottom sheets.
*   **Global Alerts (`PopupHandler`)**: Programmatic controller to trigger alerts, verification dialogs, progress bars, and screen loading blocks from view models.
*   **Automatic Lists & Grids**:
    *   `LocalList` / `LocalGridList`: Local collection rendering container with title and quick-add actions.
    *   `RemoteList` / `RemoteGridList`: Automatic remote data fetching list with loading overlays, swipe-to-refresh, and paging adapters.
*   **Overlays & Selectors**:
    *   `LocalDropDown` / `RemoteDropDown`: Filtered selection menus.
    *   `CalculatorPopup`: Interactive popup dialog calculator.
    *   `MoreControl`: Standard vertical three-dot action dropdown menus.
*   **Core Native Utilities**:
    *   `PlatformUtils`: Checks platform type (Android vs iOS), fetches package bundle version, and opens native App Store pages.
    *   `DateUtils`: LocalDateTime additions for calculations and formatting.
    *   `ImageUtils`: Platform-native rotation, resizing, and base64 parsing.
    *   `FileUtils`: Native file sharing mechanism utilizing system share sheets.
    *   `NumberUtils`: Locale currency conversion and angle unit conversions.
    *   `StringUtils`: Guid/email formats validation.
    *   `System UI`: Direct composition helpers to set navigation bar and status bar colors.
*   **Kotlinx Serializers**: Mappings to serialize Uuids and date structures cleanly between multiplatform layers.

### B. KMPControls Input Module

Provides advanced inputs, calendar picker components, and custom dialog overlays.

*   **Pickers**:
    *   `DatePicker`: Outlined text input wrapping calendar dialog overlays.
    *   `TimePicker`: Outlined text input wrapping clock dialog overlays.
    *   `DateTimePicker`: Sequential date and time inputs.
    *   `DoublePicker`: Clean numeric input for double values.
*   **Custom Dialogs**:
    *   `CalendarDialog`: Standalone compose calendar widget.
    *   `ClockDialog`: Standalone compose clock selector widget.
*   **Inputs & Indicators**:
    *   `PasswordControl`: Outlined password text field with haptic toggle visibility actions.
    *   `SearchControl`: Custom query input field with search triggers.
    *   `ClickableOutlinedTextField`: Interactive outlines field that blocks keyboard overlays, useful for routing clicks to sheets/dialogs.
    *   `AutoCompleteTextField`: Autocomplete search field showing a filtered dropdown list of suggestions.
    *   `TermsAndConditions`: Policy disclaimer container with highlight clickable link routes.
*   **Haptics (`Vibrator`)**: Trigger haptic feedback engines.

### C. KMPMaps Native Map Module

Unified layout map wrapper rendering Apple MapKit on iOS and Google Maps on Android.

*   **MapComponent**: Scaffolds coordinate focusing, zoom controls, gestures configurations, and user location markers.
*   **Vector Layers & Shapes**:
    *   `GeoPosition`: Pin location marker wrappers.
    *   `MarkerMap`: Customizable map markers with address descriptions, haptic click events, and info windows.
    *   `RouteMap`: Polyline drawing layers.
    *   `CircleMap`: Circular boundary layers.
*   **Calculations**: Distance computation between coordinates, viewport zoom calculations matching bounding circles, and fit-bounds viewport alignments.
