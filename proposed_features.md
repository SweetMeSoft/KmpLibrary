# Proposed KMP Library Enhancements

This document lists interesting features and functionalities categorized by module that would make the `SweetMeSoft` KMP Library (`kmpbase`, `kmpcontrols`, and `kmpmaps`) even more powerful and feature-rich.

---

## 1. Core & Architecture (`kmpbase`)

### Offline Caching & Sync
- [ ] **Offline-First Data Caching**: Support local SQLite (SqlDelight) or key-value storage (KStore) caching inside `RemoteList` and `RemoteDropDown` so they work offline automatically.
- [ ] **Connectivity Observer**: A cross-platform utility to observe internet connection status in real-time, displaying a subtle offline banner.

### Security & Native Storage
- [ ] **Biometric Authentication Manager**: A unified API to invoke Face ID / Touch ID (iOS) and BiometricPrompt (Android).
- [ ] **Secure Storage (Keychain / Keystore)**: A cross-platform utility to store sensitive data (like bearer tokens) securely in Android Keystore and iOS Keychain.

### UI Polish & Micro-interactions
- [ ] **Dynamic Theme Manager**: A helper class to switch dynamically between Light/Dark/System themes and apply custom primary/secondary color schemes on the fly.
- [ ] **Custom Toast & Snackbar Overlays**: Floating temporary notifications with animations and icons, replacing simple dialog alerts.
- [ ] **List Item Shimmer Effects**: Pre-built skeletons for `RemoteList` / `RemoteGridList` while loading data.

---

## 2. Pickers & Inputs (`kmpcontrols`)

### Input Validation & Formatters
- [ ] **Real-time Currency Formatter**: An as-you-type formatter for text fields that formats input into currency strings (e.g. `$1,250.00`).
- [ ] **OTP / PIN Input Field**: A standard UI widget composed of multiple separate boxes for verification codes, with automatic focus transfer.
- [ ] **Credit Card Formatter**: Automatically formats credit card numbers, expiry dates, and CVVs with credit card provider logo detection.

### Selection Widgets
- [ ] **Multi-Select Dropdown with Chips**: A dropdown widget that allows selecting multiple options, rendering them as dismissible chips.
- [ ] **File / Image Picker Overlay**: A modal popup to pick images or files from the camera, gallery, or local documents natively.

---

## 3. Location & Maps (`kmpmaps`)

### Advanced Map Rendering
- [ ] **Marker Clustering**: Automatically clusters dense markers into groups when zoomed out to improve rendering performance and map clarity.
- [ ] **Custom HTML Map Callouts**: Custom info windows for map markers that allow rendering complex Compose views (images, buttons, text layouts) instead of standard platform strings.
- [ ] **Geocoding & Reverse Geocoding**: Cross-platform address-to-coordinates and coordinates-to-address lookup helpers.
