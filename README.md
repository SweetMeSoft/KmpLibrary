# 🚀 SweetMeSoft KMP Library

Una librería multiplataforma completa para Kotlin que proporciona componentes UI avanzados, integración de mapas y utilidades esenciales para el desarrollo de aplicaciones nativas en Android, iOS y Desktop.

[![GitHub release](https://img.shields.io/github/release/erickvelasco11/KmpLibrary.svg)](https://github.com/erickvelasco11/KmpLibrary/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.7.5-green.svg)](https://developer.android.com/jetpack/compose)
[![Documentation](https://img.shields.io/badge/docs-GitHub%20Pages-blue)](https://erickvelasco11.github.io/KmpLibrary)

## 📋 Tabla de Contenidos

- [🎯 Características](#características)
- [📦 Módulos](#módulos)
- [⚡ Inicio Rápido](#inicio-rápido)
- [📚 Documentación](#documentación)
- [🛠️ Instalación](#instalación)
- [📱 Ejemplos](#ejemplos)
- [🤝 Contribuir](#contribuir)
- [📄 Licencia](#licencia)
- [👥 Autores](#autores)
- [🔗 Enlaces](#enlaces)

## ✨ Características

- **🎨 Componentes UI Modernos**: Controles personalizados con Material Design 3
- **🗺️ Integración de Mapas**: Componentes de mapas multiplataforma con Google Maps
- **📱 Soporte Completo KMP**: Compatible con Android e iOS
- **🔧 Utilidades Integradas**: Herramientas para fechas, números, imágenes y más
- **🎯 Fácil Integración**: APIs simples y bien documentadas
- **⚡ Alto Rendimiento**: Optimizado para aplicaciones de producción

## 📦 Módulos

La librería está dividida en tres módulos principales:

### 🎛️ KMPControls
Componentes UI básicos y utilidades fundamentales.
- Controles de entrada personalizados
- Selectores de fecha y hora
- Diálogos y alertas
- Utilidades de validación

### 🗺️ KMPMaps
Componentes especializados para integración de mapas.
- Componentes de mapas multiplataforma
- Marcadores y overlays personalizados
- Gestión de ubicación
- Integración con Google Maps

### 📚 Library (Principal)
Librería principal con componentes avanzados y herramientas.
- Componentes UI complejos
- Gestión de estado
- Utilidades de red
- Herramientas de navegación

### 🧪 KMPTestApp
Aplicación de ejemplo que demuestra el uso de todas las librerías.

## 🚀 Instalación

### Gradle (Kotlin DSL)

Agrega las dependencias en tu archivo `build.gradle.kts`:

```kotlin
commonMain.dependencies {
    // Librería principal
    implementation("com.sweetmesoft.kmplibrary:kmplibrary:1.6.6")
    
    // Controles básicos
    implementation("com.sweetmesoft.kmpcontrols:kmpcontrols:1.6.6")
    
    // Componentes de mapas
    implementation("com.sweetmesoft.kmpmaps:kmpmaps:1.6.6")
}
```

### Configuración de Versiones

En tu archivo `libs.versions.toml`:

```toml
[versions]
sweetmesoft = "1.6.6"

[libraries]
sweetmesoft-library = { module = "com.sweetmesoft.kmplibrary:kmplibrary", version.ref = "sweetmesoft" }
sweetmesoft-controls = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
sweetmesoft-maps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }
```

## 📋 Requisitos

- **Kotlin**: 2.2.0+
- **Compose Multiplatform**: 1.8.2+
- **Android**: API 28+ (Android 9.0)
- **iOS**: iOS 14.0+
- **Gradle**: 8.12.0+

### Dependencias Requeridas

```kotlin
// En tu build.gradle.kts
commonMain.dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("dev.icerock.moko:permissions:0.19.1")
    implementation("dev.icerock.moko:permissions-compose:0.19.1")
}
```

## ⚡ Inicio Rápido

### 1. Agregar Dependencias

En tu archivo `libs.versions.toml`:

```toml
[versions]
sweetmesoft = "1.6.6"

[libraries]
kmpcontrols = { module = "com.sweetmesoft.kmpcontrols:kmpcontrols", version.ref = "sweetmesoft" }
kmpmaps = { module = "com.sweetmesoft.kmpmaps:kmpmaps", version.ref = "sweetmesoft" }
kmplibrary = { module = "com.sweetmesoft.kmplibrary:kmplibrary", version.ref = "sweetmesoft" }
```

En tu `build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation(libs.kmpcontrols)
    implementation(libs.kmpmaps)
    implementation(libs.kmplibrary)
}
```

### 2. Usar Componentes

```kotlin
import androidx.compose.runtime.*
import com.sweetmesoft.kmpcontrols.pickers.DatePicker
import com.sweetmesoft.kmplibrary.controls.PasswordControl
import com.sweetmesoft.kmpmaps.MapComponent

@Composable
fun MyApp() {
    var selectedDate by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Column {
        // Control de contraseña
        PasswordControl(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña"
        )
        
        // Selector de fecha
        DatePicker(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it }
        )
        
        // Componente de mapa
        MapComponent(
            modifier = Modifier.fillMaxSize(),
            initialPosition = GeoPosition(40.7128, -74.0060)
        )
    }
}
```

## 📚 Documentación

### 🌐 Complete Documentation

**[📖 Visit our complete documentation on GitHub Pages](https://erickvelasco11.github.io/KmpLibrary)**

The documentation includes:

- 🚀 **Quick start guides** for each module
- 📝 **Detailed API** with code examples
- 🎨 **Customization guides** and themes
- 🧪 **Practical examples** and use cases
- 🔧 **Advanced configuration** for each platform
- ❓ **Common troubleshooting** solutions

### 📋 Documentation by Module

| Module | Description | Documentation |
|--------|-------------|---------------|
| 🎛️ **KMPControls** | Basic UI components and utilities | [View Docs](https://erickvelasco11.github.io/KmpLibrary/kmpcontrols) |
| 🗺️ **KMPMaps** | Cross-platform maps integration | [View Docs](https://erickvelasco11.github.io/KmpLibrary/kmpmaps) |
| 📚 **Library** | Advanced components and tools | [View Docs](https://erickvelasco11.github.io/KmpLibrary/library) |
| 🧪 **KMPTestApp** | Example application and guides | [View Docs](https://erickvelasco11.github.io/KmpLibrary/kmptestapp) |

### 📖 Local Documentation

You can also access the documentation locally:

- **[KMPControls](docs/kmpcontrols.md)** - Basic UI components
- **[KMPMaps](docs/kmpmaps.md)** - Maps integration
- **[Library](docs/library.md)** - Main module
- **[KMPTestApp](docs/kmptestapp.md)** - Usage examples

## 🔧 Configuración del Proyecto

### Android

En tu `build.gradle.kts` de Android:

```kotlin
android {
    compileSdk = 36
    defaultConfig {
        minSdk = 28
        targetSdk = 36
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
```

### iOS

Asegúrate de tener configurado el deployment target mínimo:

```kotlin
iosX64()
iosArm64()
iosSimulatorArm64()
```

## 🎨 Personalización

### Temas Personalizados

```kotlin
@Composable
fun MyCustomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6)
        )
    ) {
        content()
    }
}
```

## 🧪 Ejemplos

El proyecto incluye una aplicación de ejemplo completa en el módulo `kmptestapp` que demuestra:

- Uso de todos los componentes UI
- Integración de mapas
- Navegación entre pantallas
- Gestión de estado
- Mejores prácticas de desarrollo

Para ejecutar la aplicación de ejemplo:

```bash
./gradlew :kmptestapp:run
```

## 🤝 Contributing

Contributions are welcome! We have a complete guide for contributors.

**[📋 Read our Contributing Guide](CONTRIBUTING.md)**

### Ways to Contribute

- 🐛 **Report bugs** - Help us improve by reporting issues
- 💡 **Suggest features** - Share your ideas for new functionalities
- 📝 **Improve documentation** - Help make documentation clearer
- 💻 **Contribute code** - Implement new features or fix bugs
- 🧪 **Write tests** - Improve test coverage
- 🌍 **Translate** - Help make the library more accessible

### Quick Start for Contributors

```bash
# 1. Fork and clone the repository
git clone https://github.com/YOUR_USERNAME/KmpLibrary.git
cd KmpLibrary

# 2. Create a branch for your feature
git checkout -b feature/my-new-feature

# 3. Install dependencies and build
./gradlew build

# 4. Run tests
./gradlew test

# 5. Test the example app
./gradlew :kmptestapp:installDebug
```

### Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to maintain a respectful and welcoming environment for everyone.

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Autores

- **Erick Velasco** - *Desarrollo Principal* - [erick.velasco@sweetmesoft.com](mailto:erick.velasco@sweetmesoft.com)

## 🔗 Useful Links

### 📚 Documentation and Resources
- **[📖 Complete Documentation](https://erickvelasco11.github.io/KmpLibrary)** - GitHub Pages
- **[📋 Contributing Guide](CONTRIBUTING.md)** - How to contribute to the project
- **[🧪 Example Application](kmptestapp/)** - Example source code
- **[📝 Changelog](https://github.com/erickvelasco11/KmpLibrary/releases)** - Change history

### 🛠️ Development
- **[🏠 Main Repository](https://github.com/erickvelasco11/KmpLibrary)** - Source code
- **[🐛 Issues and Bug Reports](https://github.com/erickvelasco11/KmpLibrary/issues)** - Report problems
- **[🚀 Releases](https://github.com/erickvelasco11/KmpLibrary/releases)** - Published versions
- **[📦 Maven Central](https://search.maven.org/search?q=g:com.sweetmesoft)** - Published packages

### 🌐 Community
- **[💬 Discussions](https://github.com/erickvelasco11/KmpLibrary/discussions)** - Questions and discussions
- **[📧 Contact](mailto:team@sweetmesoft.com)** - Direct contact
- **[🐦 Twitter](https://twitter.com/sweetmesoft)** - Updates and news

## 📊 Project Status

| Aspect | Status |
|---------|--------|
| **Current Version** | ![Version](https://img.shields.io/badge/version-1.6.6-blue) |
| **Status** | ![Status](https://img.shields.io/badge/status-Active%20development-green) |
| **Kotlin** | ![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-purple) |
| **Compose** | ![Compose](https://img.shields.io/badge/Compose-1.7.5-orange) |
| **Platforms** | ![Platforms](https://img.shields.io/badge/platforms-Android%20%7C%20iOS%20%7C%20Desktop-lightgrey) |
| **License** | ![License](https://img.shields.io/badge/license-MIT-yellow) |
| **Tests** | ![Tests](https://img.shields.io/badge/tests-passing-brightgreen) |
| **Documentation** | ![Docs](https://img.shields.io/badge/docs-complete-blue) |

### 🎯 Roadmap

- ✅ **v1.6.x** - Basic components and maps
- 🚧 **v1.7.x** - Performance improvements and new components
- 📋 **v1.8.x** - Support for more platforms
- 🔮 **v2.0.x** - Renewed API and advanced features

---

<div align="center">

**Do you like SweetMeSoft KMP Library?**

⭐ **[Give it a star on GitHub](https://github.com/erickvelasco11/KmpLibrary)** ⭐

**[📖 Explore Documentation](https://erickvelasco11.github.io/KmpLibrary)** • **[🚀 Get Started Now](#quick-start)** • **[🤝 Contribute](CONTRIBUTING.md)**

</div>

---

**Need help?** Open an [issue](https://github.com/erickvelasco11/KmpLibrary/issues) or contact the development team.
