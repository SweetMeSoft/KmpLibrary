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

### 🌐 Documentación Completa

**[📖 Visita nuestra documentación completa en GitHub Pages](https://erickvelasco11.github.io/KmpLibrary)**

La documentación incluye:

- 🚀 **Guías de inicio rápido** para cada módulo
- 📝 **API detallada** con ejemplos de código
- 🎨 **Guías de personalización** y temas
- 🧪 **Ejemplos prácticos** y casos de uso
- 🔧 **Configuración avanzada** para cada plataforma
- ❓ **Solución de problemas** comunes

### 📋 Documentación por Módulo

| Módulo | Descripción | Documentación |
|--------|-------------|---------------|
| 🎛️ **KMPControls** | Componentes UI básicos y utilidades | [Ver Docs](https://erickvelasco11.github.io/KmpLibrary/kmpcontrols) |
| 🗺️ **KMPMaps** | Integración de mapas multiplataforma | [Ver Docs](https://erickvelasco11.github.io/KmpLibrary/kmpmaps) |
| 📚 **Library** | Componentes avanzados y herramientas | [Ver Docs](https://erickvelasco11.github.io/KmpLibrary/library) |
| 🧪 **KMPTestApp** | Aplicación de ejemplo y guías | [Ver Docs](https://erickvelasco11.github.io/KmpLibrary/kmptestapp) |

### 📖 Documentación Local

También puedes acceder a la documentación localmente:

- **[KMPControls](docs/kmpcontrols.md)** - Componentes UI básicos
- **[KMPMaps](docs/kmpmaps.md)** - Integración de mapas
- **[Library](docs/library.md)** - Módulo principal
- **[KMPTestApp](docs/kmptestapp.md)** - Ejemplos de uso

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

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Tenemos una guía completa para contribuidores.

**[📋 Lee nuestra Guía de Contribución](CONTRIBUTING.md)**

### Formas de Contribuir

- 🐛 **Reportar bugs** - Ayúdanos a mejorar reportando problemas
- 💡 **Sugerir características** - Comparte tus ideas para nuevas funcionalidades
- 📝 **Mejorar documentación** - Ayuda a que la documentación sea más clara
- 💻 **Contribuir código** - Implementa nuevas características o corrige bugs
- 🧪 **Escribir tests** - Mejora la cobertura de pruebas
- 🌍 **Traducir** - Ayuda a hacer la librería más accesible

### Inicio Rápido para Contribuidores

```bash
# 1. Fork y clona el repositorio
git clone https://github.com/TU_USUARIO/KmpLibrary.git
cd KmpLibrary

# 2. Crea una rama para tu característica
git checkout -b feature/mi-nueva-caracteristica

# 3. Instala dependencias y construye
./gradlew build

# 4. Ejecuta tests
./gradlew test

# 5. Prueba la app de ejemplo
./gradlew :kmptestapp:installDebug
```

### Código de Conducta

Este proyecto se adhiere a un código de conducta. Al participar, se espera que mantengas un ambiente respetuoso y acogedor para todos.

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Autores

- **Erick Velasco** - *Desarrollo Principal* - [erick.velasco@sweetmesoft.com](mailto:erick.velasco@sweetmesoft.com)

## 🔗 Enlaces Útiles

### 📚 Documentación y Recursos
- **[📖 Documentación Completa](https://erickvelasco11.github.io/KmpLibrary)** - GitHub Pages
- **[📋 Guía de Contribución](CONTRIBUTING.md)** - Cómo contribuir al proyecto
- **[🧪 Aplicación de Ejemplo](kmptestapp/)** - Código fuente de ejemplos
- **[📝 Changelog](https://github.com/erickvelasco11/KmpLibrary/releases)** - Historial de cambios

### 🛠️ Desarrollo
- **[🏠 Repositorio Principal](https://github.com/erickvelasco11/KmpLibrary)** - Código fuente
- **[🐛 Issues y Bug Reports](https://github.com/erickvelasco11/KmpLibrary/issues)** - Reportar problemas
- **[🚀 Releases](https://github.com/erickvelasco11/KmpLibrary/releases)** - Versiones publicadas
- **[📦 Maven Central](https://search.maven.org/search?q=g:com.sweetmesoft)** - Paquetes publicados

### 🌐 Comunidad
- **[💬 Discusiones](https://github.com/erickvelasco11/KmpLibrary/discussions)** - Preguntas y discusiones
- **[📧 Contacto](mailto:team@sweetmesoft.com)** - Contacto directo
- **[🐦 Twitter](https://twitter.com/sweetmesoft)** - Actualizaciones y noticias

## 📊 Estado del Proyecto

| Aspecto | Estado |
|---------|--------|
| **Versión Actual** | ![Version](https://img.shields.io/badge/version-1.6.6-blue) |
| **Estado** | ![Status](https://img.shields.io/badge/status-En%20desarrollo%20activo-green) |
| **Kotlin** | ![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-purple) |
| **Compose** | ![Compose](https://img.shields.io/badge/Compose-1.7.5-orange) |
| **Plataformas** | ![Platforms](https://img.shields.io/badge/platforms-Android%20%7C%20iOS%20%7C%20Desktop-lightgrey) |
| **Licencia** | ![License](https://img.shields.io/badge/license-MIT-yellow) |
| **Tests** | ![Tests](https://img.shields.io/badge/tests-passing-brightgreen) |
| **Documentación** | ![Docs](https://img.shields.io/badge/docs-completa-blue) |

### 🎯 Roadmap

- ✅ **v1.6.x** - Componentes básicos y mapas
- 🚧 **v1.7.x** - Mejoras de rendimiento y nuevos componentes
- 📋 **v1.8.x** - Soporte para más plataformas
- 🔮 **v2.0.x** - API renovada y características avanzadas

---

<div align="center">

**¿Te gusta SweetMeSoft KMP Library?**

⭐ **[Dale una estrella en GitHub](https://github.com/erickvelasco11/KmpLibrary)** ⭐

**[📖 Explorar Documentación](https://erickvelasco11.github.io/KmpLibrary)** • **[🚀 Comenzar Ahora](#inicio-rápido)** • **[🤝 Contribuir](CONTRIBUTING.md)**

</div>

---

**¿Necesitas ayuda?** Abre un [issue](https://github.com/erickvelasco11/KmpLibrary/issues) o contacta al equipo de desarrollo.
