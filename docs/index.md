---
layout: default
title: Home
nav_order: 1
description: "Official documentation for the SweetMeSoft KMP Library. Build responsive, native, cross-platform Android and iOS applications with Compose Multiplatform."
permalink: /
---

# SweetMeSoft Kotlin Multiplatform Library

<div class="banner-container" style="background-image: url('{{ site.baseurl }}/assets/images/banner.png'); background-size: cover; background-position: center; border-radius: 12px; padding: 48px; margin: 24px 0; color: white; text-align: center; box-shadow: 0 4px 20px rgba(0,0,0,0.15);">
  <img src="{{ site.baseurl }}/assets/images/logo.png" alt="SweetMeSoft Logo" style="max-height: 80px; margin-bottom: 16px; filter: drop-shadow(0 2px 8px rgba(0,0,0,0.3));" />
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
    <a href="{{ site.baseurl }}/docs/kmpbase/" class="btn" style="display: inline-block; padding: 8px 16px; background: #61afef; color: #1e1e1e; font-weight: 600; text-decoration: none; border-radius: 4px;">Explore KMPBase</a>
  </div>
  
  <div class="module-card" style="border: 1px solid #e1e4e8; border-radius: 8px; padding: 24px; background: #1f242e; color: #f8f9fa; transition: transform 0.2s; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
    <h3 style="color: #98c379; margin-top: 0;">KMPControls</h3>
    <p style="font-size: 0.95rem; color: #abb2bf; min-height: 72px;">Premium UI pickers (Date, Time, DateTime), password fields, autocomplete widgets, terms and conditions screens, and customizable OTP/PIN inputs.</p>
    <a href="{{ site.baseurl }}/docs/kmpcontrols/" class="btn" style="display: inline-block; padding: 8px 16px; background: #98c379; color: #1e1e1e; font-weight: 600; text-decoration: none; border-radius: 4px;">Explore KMPControls</a>
  </div>
  
  <div class="module-card" style="border: 1px solid #e1e4e8; border-radius: 8px; padding: 24px; background: #1f242e; color: #f8f9fa; transition: transform 0.2s; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
    <h3 style="color: #e5c07b; margin-top: 0;">KMPMaps</h3>
    <p style="font-size: 0.95rem; color: #abb2bf; min-height: 72px;">Unified mapping component wrapping Google Maps (Android) and MapKit (iOS) supporting markers, path routing, geometric overlays, and GPS location tracking.</p>
    <a href="{{ site.baseurl }}/docs/kmpmaps/" class="btn" style="display: inline-block; padding: 8px 16px; background: #e5c07b; color: #1e1e1e; font-weight: 600; text-decoration: none; border-radius: 4px;">Explore KMPMaps</a>
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

## Need Help?

- Read the [Getting Started Guide]({{ site.baseurl }}/docs/getting-started/) to configure your first application.
- Explore [KMPTestApp Guide]({{ site.baseurl }}/docs/kmptestapp/) to see real-world implementation samples.
- Visit our [GitHub Repository](https://github.com/erickvelasco11/KmpLibrary) to report bugs or request features.
