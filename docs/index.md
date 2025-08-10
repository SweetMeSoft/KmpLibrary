---
layout: default
title: Inicio
nav_order: 1
---

# 🚀 SweetMeSoft KMP Library

¡Bienvenido a la documentación oficial de SweetMeSoft KMP Library! Una librería multiplataforma completa para Kotlin que te permite crear aplicaciones nativas para Android, iOS y Desktop con un código base compartido.

## 🎯 ¿Qué es SweetMeSoft KMP Library?

SweetMeSoft KMP Library es una colección de módulos especializados que proporcionan:

- **🎛️ Componentes UI avanzados** para interfaces modernas
- **🗺️ Integración de mapas** multiplataforma
- **🔧 Utilidades y herramientas** para desarrollo ágil
- **📱 Ejemplos prácticos** y mejores prácticas

## 📦 Módulos Disponibles

<div class="module-grid">
  <div class="module-card">
    <h3>🎛️ KMPControls</h3>
    <p>Componentes UI fundamentales como selectores de fecha, diálogos, campos de texto y utilidades de validación.</p>
    <a href="kmpcontrols.html" class="btn">Ver Documentación</a>
  </div>
  
  <div class="module-card">
    <h3>🗺️ KMPMaps</h3>
    <p>Integración completa de mapas con Google Maps para Android y MapKit para iOS, incluyendo marcadores y geolocalización.</p>
    <a href="kmpmaps.html" class="btn">Ver Documentación</a>
  </div>
  
  <div class="module-card">
    <h3>📚 Library</h3>
    <p>Módulo principal con componentes avanzados, navegación, utilidades de red, manejo de imágenes y arquitectura base.</p>
    <a href="library.html" class="btn">Ver Documentación</a>
  </div>
  
  <div class="module-card">
    <h3>🧪 KMPTestApp</h3>
    <p>Aplicación de ejemplo que demuestra el uso de todos los módulos con implementaciones prácticas y mejores prácticas.</p>
    <a href="kmptestapp.html" class="btn">Ver Documentación</a>
  </div>
</div>

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

### 2. Configurar tu Aplicación

```kotlin
@Composable
fun App() {
    MaterialTheme {
        Navigator(screen = MainScreen()) { navigator ->
            BaseViewModel.navigator = navigator
            SlideTransition(navigator)
        }
    }
}
```

### 3. Usar Componentes

```kotlin
@Composable
fun MyScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var password by remember { mutableStateOf("") }
    
    Column {
        // Selector de fecha de KMPControls
        ClickableOutlinedTextField(
            value = selectedDate?.toString() ?: "",
            label = "Fecha",
            onClick = { /* Mostrar DatePicker */ }
        )
        
        // Control de contraseña de Library
        PasswordControl(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña"
        )
        
        // Mapa de KMPMaps
        MapComponent(
            modifier = Modifier.height(200.dp),
            initialPosition = GeoPosition(40.7128, -74.0060)
        )
    }
}
```

## 🛠️ Características Principales

### ✨ Multiplataforma Real
- **Android** con Jetpack Compose
- **iOS** con SwiftUI integration
- **Desktop** (experimental)
- Código compartido al máximo

### 🎨 Componentes Modernos
- Material Design 3
- Temas adaptativos (claro/oscuro)
- Animaciones fluidas
- Accesibilidad integrada

### 🗺️ Mapas Nativos
- Google Maps en Android
- MapKit en iOS
- API unificada
- Marcadores personalizables

### 🔧 Utilidades Completas
- Validación de formularios
- Manejo de imágenes
- Operaciones de red
- Navegación avanzada

### 📱 Arquitectura Sólida
- MVVM pattern
- StateFlow para estados reactivos
- Navegación declarativa
- Manejo de errores centralizado

## 📖 Guías de Documentación

<div class="guide-links">
  <a href="kmpcontrols.html" class="guide-link">
    <h4>🎛️ Guía de KMPControls</h4>
    <p>Aprende a usar selectores, diálogos y validaciones</p>
  </a>
  
  <a href="kmpmaps.html" class="guide-link">
    <h4>🗺️ Guía de KMPMaps</h4>
    <p>Implementa mapas nativos en tu aplicación</p>
  </a>
  
  <a href="library.html" class="guide-link">
    <h4>📚 Guía de Library</h4>
    <p>Domina los componentes avanzados y utilidades</p>
  </a>
  
  <a href="kmptestapp.html" class="guide-link">
    <h4>🧪 Ejemplos Prácticos</h4>
    <p>Ve implementaciones reales y mejores prácticas</p>
  </a>
</div>

## 🚀 Proyectos de Ejemplo

Explora nuestros ejemplos completos:

- **[KMPTestApp](kmptestapp.html)** - Aplicación de demostración completa
- **[Formularios Avanzados](library.html#ejemplos-de-uso)** - Validación y controles
- **[Integración de Mapas](kmpmaps.html#ejemplos-de-uso)** - Geolocalización y marcadores
- **[Navegación Compleja](library.html#navegación)** - Flujos de navegación

## 🤝 Comunidad y Soporte

- **📚 [Documentación Completa](https://github.com/erickvelasco11/KmpLibrary/blob/main/README.md)**
- **🐛 [Reportar Issues](https://github.com/erickvelasco11/KmpLibrary/issues)**
- **💡 [Solicitar Características](https://github.com/erickvelasco11/KmpLibrary/issues/new)**
- **🔄 [Contribuir](https://github.com/erickvelasco11/KmpLibrary/blob/main/CONTRIBUTING.md)**

## 📊 Estado del Proyecto

- **Versión Actual:** 1.6.6
- **Kotlin:** 2.1.0
- **Compose:** 1.7.5
- **Plataformas:** Android, iOS, Desktop (experimental)
- **Licencia:** MIT

---

<div class="footer-cta">
  <h3>¿Listo para empezar?</h3>
  <p>Comienza con nuestra guía de instalación y crea tu primera aplicación multiplataforma.</p>
  <a href="https://github.com/erickvelasco11/KmpLibrary" class="btn btn-primary">Ver en GitHub</a>
  <a href="kmpcontrols.html" class="btn btn-secondary">Comenzar Tutorial</a>
</div>

<style>
.module-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin: 30px 0;
}

.module-card {
  border: 1px solid #e1e4e8;
  border-radius: 8px;
  padding: 20px;
  background: #f8f9fa;
}

.module-card h3 {
  margin-top: 0;
  color: #0366d6;
}

.btn {
  display: inline-block;
  padding: 8px 16px;
  background: #0366d6;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  margin-top: 10px;
}

.btn:hover {
  background: #0256cc;
}

.btn-secondary {
  background: #6c757d;
}

.btn-secondary:hover {
  background: #5a6268;
}

.guide-links {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 15px;
  margin: 20px 0;
}

.guide-link {
  display: block;
  padding: 15px;
  border: 1px solid #e1e4e8;
  border-radius: 6px;
  text-decoration: none;
  color: inherit;
  transition: all 0.2s;
}

.guide-link:hover {
  border-color: #0366d6;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.guide-link h4 {
  margin: 0 0 8px 0;
  color: #0366d6;
}

.guide-link p {
  margin: 0;
  color: #586069;
  font-size: 14px;
}

.footer-cta {
  text-align: center;
  margin: 40px 0;
  padding: 30px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 8px;
}

.footer-cta h3 {
  margin-top: 0;
}

.footer-cta .btn {
  margin: 0 10px;
}
</style>