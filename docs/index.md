---
layout: default
title: Home
nav_order: 1
description: "Official documentation for the SweetMeSoft KMP Library. Build responsive, native, cross-platform Android and iOS applications with Compose Multiplatform."
---

# SweetMeSoft Kotlin Multiplatform Library

<div class="banner-container" style="background-image: url('{{ site.baseurl }}/assets/images/banner.png'); background-size: cover; background-position: center; border-radius: 12px; padding: 48px; margin: 24px 0; color: white; text-align: center; box-shadow: 0 4px 20px rgba(0,0,0,0.15);">
  <img src="{{ site.baseurl }}/assets/images/sweetmesoft_logo_transparent.png" alt="SweetMeSoft Logo" style="max-height: 80px; margin-bottom: 16px; filter: drop-shadow(0 2px 8px rgba(0,0,0,0.3));" />
  <h1 style="color: white; margin: 0; font-size: 2.25rem; font-weight: 800; text-shadow: 0 2px 4px rgba(0,0,0,0.5);">SweetMeSoft KMP Library</h1>
  <p style="font-size: 1.15rem; margin: 12px 0 0 0; text-shadow: 0 1px 3px rgba(0,0,0,0.5); opacity: 0.95;">A premium, modern multiplatform library for Kotlin providing advanced UI components, native maps integration, and essential utilities.</p>
</div>

Welcome to the official documentation! SweetMeSoft KMP Library is designed to accelerate cross-platform mobile development by sharing codebases between **Android** and **iOS** using **Compose Multiplatform** while maintaining a native look, feel, and performance.

---

## Library Architecture

The library is split into three core modules:

<div class="module-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 20px; margin: 24px 0;">
  <div class="module-card" style="border: 1px solid #e1e4e8; border-radius: 8px; padding: 24px; background: #1f242e; color: #f8f9fa; transition: transform 0.2s; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
    <h3 style="color: #61afef; margin-top: 0;">KMPBase</h3>
    <p style="font-size: 0.95rem; color: #abb2bf; min-height: 72px;">Core architecture foundation including base ViewModel and Screen setups, loading overlays, network clients, dialog utilities, and platforms helpers.</p>
    <a href="{{ site.baseurl }}/kmpbase.html" class="btn" style="display: inline-block; padding: 8px 16px; background: #61afef; color: #1e1e1e; font-weight: 600; text-decoration: none; border-radius: 4px;">Explore KMPBase</a>
  </div>
  
  <div class="module-card" style="border: 1px solid #e1e4e8; border-radius: 8px; padding: 24px; background: #1f242e; color: #f8f9fa; transition: transform 0.2s; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
    <h3 style="color: #98c379; margin-top: 0;">KMPControls</h3>
    <p style="font-size: 0.95rem; color: #abb2bf; min-height: 72px;">Premium UI pickers (Date, Time, DateTime), password fields, autocomplete widgets, terms and conditions screens, and customizable OTP/PIN inputs.</p>
    <a href="{{ site.baseurl }}/kmpcontrols.html" class="btn" style="display: inline-block; padding: 8px 16px; background: #98c379; color: #1e1e1e; font-weight: 600; text-decoration: none; border-radius: 4px;">Explore KMPControls</a>
  </div>
  
  <div class="module-card" style="border: 1px solid #e1e4e8; border-radius: 8px; padding: 24px; background: #1f242e; color: #f8f9fa; transition: transform 0.2s; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
    <h3 style="color: #e5c07b; margin-top: 0;">KMPMaps</h3>
    <p style="font-size: 0.95rem; color: #abb2bf; min-height: 72px;">Unified mapping component wrapping Google Maps (Android) and MapKit (iOS) supporting markers, path routing, geometric overlays, and GPS location tracking.</p>
    <a href="{{ site.baseurl }}/kmpmaps.html" class="btn" style="display: inline-block; padding: 8px 16px; background: #e5c07b; color: #1e1e1e; font-weight: 600; text-decoration: none; border-radius: 4px;">Explore KMPMaps</a>
  </div>
</div>

---

## Quick Start in 3 Steps

### 1. Add Dependencies

Add the version and libraries in your `gradle/libs.versions.toml` file:

```toml
[versions]
sweetmesoft = "2.2.1"

[libraries]
sweetmesoft-kmpbase = { module = "com.sweetmesoft.kmpbase:kmpbase", version.ref = "sweetmesoft" }
sweetmesoft-kmpcontrols = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
sweetmesoft-kmpmaps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }
```

In your shared module's `build.gradle.kts` (under `commonMain` dependencies):

```kotlin
implementation(libs.sweetmesoft.kmpbase)
implementation(libs.sweetmesoft.kmpcontrols)
implementation(libs.sweetmesoft.kmpmaps)
```

### 2. Initialize in MainActivity (Android)

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sweetmesoft.kmpbase.BaseAndroid.Companion.initSweetMeSoft

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize KMP library core platform context
        initSweetMeSoft(this)
        
        setContent {
            App()
        }
    }
}
```

### 3. Connect Voyager Navigation

```kotlin
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.sweetmesoft.kmpbase.base.BaseViewModel

@Composable
fun App() {
    Navigator(screen = MainScreen()) { navigator ->
        // Cache the navigator globally to support ViewModel routing
        BaseViewModel.navigator = navigator
        SlideTransition(navigator)
    }
}
```

---

## Core Features

- **True Multiplatform**: Unified UI and logic compiles natively for Android (Jetpack Compose) and iOS (UIKit / Compose).
- **Modern M3 Design**: High-fidelity, accessibile Material Design 3 styling supporting light/dark theme switching out-of-the-box.
- **Robust Networking**: Seamless Http Client wrappers integrated directly with loading overlays.
- **Biometric Integration**: Unified fingerprint/FaceID status check and prompt verification API.

---

## Try KMPTestApp Example

Experience the unified KMP UI controls, biometrics, and native maps directly on your Android or iOS device:

<div class="download-buttons" style="display: flex; gap: 16px; margin: 16px 0; align-items: center;">
  <a href="https://play.google.com/store/apps/details?id=com.sweetmesoft.kmptestapp" target="_blank" style="display: inline-block;">
    <img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" alt="Get it on Google Play" style="height: 55px; object-fit: contain; margin-left: -10px;" />
  </a>
  <a href="https://apps.apple.com/app/sweetmesoft-kmptestapp" target="_blank" style="display: flex; align-items: center; justify-content: center; height: 38px; background: black; color: white; border-radius: 6px; padding: 0 16px; text-decoration: none; border: 1px solid #555; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; font-size: 13px; font-weight: 500;">
    <svg viewBox="0 0 170 170" style="height: 18px; fill: white; margin-right: 8px;"><path d="M150.37 130.25c-2.45 5.66-5.35 10.87-8.71 15.66-4.58 6.53-8.33 11.05-11.22 13.56-4.48 4.12-9.28 6.23-14.42 6.35-3.69 0-8.14-1.05-13.32-3.18-5.19-2.12-9.97-3.17-14.34-3.17-4.58 0-9.49 1.05-14.75 3.17-5.26 2.13-9.5 3.24-12.74 3.35-4.37.13-9.13-1.9-14.28-6.08-3.48-2.83-7.39-7.53-11.73-14.12-7.23-11.09-12.7-24.64-16.4-40.63-3.7-16-5.56-30.82-5.56-44.47 0-14.92 3.48-27.1 10.45-36.57 6.96-9.47 16.03-14.28 27.21-14.41 6.03-.13 12.18 1.63 18.45 5.27 6.27 3.64 10.84 5.27 13.72 4.88 2.62-.35 7.15-2.09 13.57-5.22 6.43-3.13 12.35-4.57 17.77-4.32 15.65.63 27.48 6.39 35.48 17.27-14.92 9.07-22.25 21.36-22 36.88.25 12.03 4.9 22.12 13.93 30.25 9.03 8.13 19.53 12.63 31.48 13.5-2.43 7.01-5.7 13.62-9.82 19.82zM119.22 19.14c0-7.72 2.76-14.88 8.28-21.49 1.42-1.63 3.01-3.21 4.79-4.75.12-.1.2-.1.25-.01.12.2.22.45.29.74 1.5 5.92-.09 12.1-4.79 18.52-4.7 6.42-10.22 10.59-16.53 12.51-.12.04-.21.01-.26-.1-.13-.42-.23-.84-.28-1.26-.52-1.42-.79-2.82-.79-4.16z"/></svg>
    Download on App Store
  </a>
</div>

---

## Need Help?

- Read the [Getting Started Guide]({{ site.baseurl }}/getting-started.html) to configure your first application.
- Explore [KMPTestApp Guide]({{ site.baseurl }}/kmptestapp.html) to see real-world implementation samples.
- Visit our [GitHub Repository](https://github.com/erickvelasco11/KmpLibrary) to report bugs or request features.
