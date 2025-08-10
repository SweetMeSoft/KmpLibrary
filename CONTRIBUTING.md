# 🤝 Guía de Contribución

¡Gracias por tu interés en contribuir a SweetMeSoft KMP Library! Esta guía te ayudará a entender cómo puedes participar en el desarrollo de la librería.

## 📋 Tabla de Contenidos

- [Código de Conducta](#código-de-conducta)
- [¿Cómo Contribuir?](#cómo-contribuir)
- [Reportar Issues](#reportar-issues)
- [Solicitar Características](#solicitar-características)
- [Contribuir con Código](#contribuir-con-código)
- [Estándares de Código](#estándares-de-código)
- [Proceso de Pull Request](#proceso-de-pull-request)
- [Configuración del Entorno](#configuración-del-entorno)

## 📜 Código de Conducta

Este proyecto se adhiere a un código de conducta. Al participar, se espera que mantengas este código. Por favor reporta comportamientos inaceptables a [team@sweetmesoft.com](mailto:team@sweetmesoft.com).

### Nuestros Compromisos

- Usar un lenguaje acogedor e inclusivo
- Respetar diferentes puntos de vista y experiencias
- Aceptar críticas constructivas de manera elegante
- Enfocarse en lo que es mejor para la comunidad
- Mostrar empatía hacia otros miembros de la comunidad

## 🚀 ¿Cómo Contribuir?

Hay muchas formas de contribuir a SweetMeSoft KMP Library:

### 🐛 Reportar Bugs
- Busca primero en los [issues existentes](https://github.com/erickvelasco11/KmpLibrary/issues)
- Si no encuentras un issue similar, crea uno nuevo
- Proporciona información detallada sobre el problema

### 💡 Sugerir Mejoras
- Revisa las [características planificadas](https://github.com/erickvelasco11/KmpLibrary/projects)
- Abre un issue con la etiqueta "enhancement"
- Describe claramente la mejora propuesta

### 📝 Mejorar Documentación
- Corrige errores tipográficos
- Mejora explicaciones existentes
- Agrega ejemplos de uso
- Traduce documentación

### 💻 Contribuir con Código
- Implementa nuevas características
- Corrige bugs existentes
- Mejora el rendimiento
- Agrega tests

## 🐛 Reportar Issues

Cuando reportes un bug, incluye:

### Información del Entorno
```
- Versión de la librería: [ej. 1.6.6]
- Plataforma: [Android/iOS/Desktop]
- Versión del SO: [ej. Android 14, iOS 17.0]
- Versión de Kotlin: [ej. 2.1.0]
- Versión de Compose: [ej. 1.7.5]
```

### Descripción del Problema
- **Resumen:** Descripción breve del problema
- **Pasos para reproducir:** Lista detallada de pasos
- **Comportamiento esperado:** Qué debería pasar
- **Comportamiento actual:** Qué está pasando
- **Capturas de pantalla:** Si aplica
- **Logs/Errores:** Mensajes de error relevantes

### Plantilla de Issue
```markdown
## 🐛 Descripción del Bug
Descripción clara y concisa del problema.

## 🔄 Pasos para Reproducir
1. Ve a '...'
2. Haz clic en '...'
3. Desplázate hacia '...'
4. Ve el error

## ✅ Comportamiento Esperado
Descripción de lo que esperabas que pasara.

## ❌ Comportamiento Actual
Descripción de lo que realmente pasó.

## 📱 Entorno
- Versión de la librería: 
- Plataforma: 
- Versión del SO: 
- Dispositivo: 

## 📎 Información Adicional
Cualquier otra información relevante sobre el problema.
```

## 💡 Solicitar Características

Para solicitar una nueva característica:

1. **Verifica** que no exista ya una solicitud similar
2. **Describe** el problema que resuelve la característica
3. **Explica** la solución propuesta
4. **Considera** alternativas que hayas evaluado
5. **Proporciona** ejemplos de uso

### Plantilla de Feature Request
```markdown
## 🚀 Descripción de la Característica
Descripción clara de la característica solicitada.

## 🎯 Problema que Resuelve
¿Qué problema específico resuelve esta característica?

## 💡 Solución Propuesta
Descripción detallada de cómo debería funcionar.

## 🔄 Alternativas Consideradas
¿Qué otras soluciones has considerado?

## 📝 Ejemplos de Uso
```kotlin
// Ejemplo de cómo se usaría la característica
```

## 📊 Impacto
- ¿Qué módulos se verían afectados?
- ¿Es un cambio breaking?
- ¿Requiere migración?
```

## 💻 Contribuir con Código

### Configuración del Entorno

#### Requisitos
- **JDK 17** o superior
- **Android Studio** (última versión estable)
- **Xcode** (para desarrollo iOS)
- **Git**

#### Configuración Inicial
```bash
# 1. Fork el repositorio
# 2. Clona tu fork
git clone https://github.com/TU_USUARIO/KmpLibrary.git
cd KmpLibrary

# 3. Agrega el repositorio original como upstream
git remote add upstream https://github.com/erickvelasco11/KmpLibrary.git

# 4. Crea una rama para tu característica
git checkout -b feature/mi-nueva-caracteristica

# 5. Instala dependencias
./gradlew build
```

### Estructura del Proyecto
```
KmpLibrary/
├── kmpcontrols/          # Componentes UI básicos
├── kmpmaps/              # Integración de mapas
├── library/              # Módulo principal
├── kmptestapp/           # Aplicación de prueba
├── docs/                 # Documentación
└── .github/              # Configuración de GitHub
```

### Flujo de Desarrollo

1. **Sincroniza** tu fork con upstream
```bash
git fetch upstream
git checkout main
git merge upstream/main
```

2. **Crea** una rama para tu característica
```bash
git checkout -b feature/descripcion-breve
```

3. **Desarrolla** tu característica
   - Escribe código limpio y bien documentado
   - Agrega tests para nueva funcionalidad
   - Actualiza documentación si es necesario

4. **Prueba** tu código
```bash
# Ejecutar tests
./gradlew test

# Verificar que la app de prueba funciona
./gradlew :kmptestapp:installDebug
```

5. **Commit** tus cambios
```bash
git add .
git commit -m "feat: agregar nueva característica X"
```

6. **Push** a tu fork
```bash
git push origin feature/descripcion-breve
```

7. **Crea** un Pull Request

## 📏 Estándares de Código

### Convenciones de Naming

#### Kotlin
```kotlin
// Clases: PascalCase
class DatePickerDialog

// Funciones y variables: camelCase
fun showDatePicker()
val selectedDate = LocalDate.now()

// Constantes: SCREAMING_SNAKE_CASE
const val DEFAULT_TIMEOUT = 5000

// Composables: PascalCase
@Composable
fun CustomButton() { }
```

#### Archivos
```
// Clases: NombreClase.kt
DatePickerDialog.kt

// Composables: NombreComposable.kt
CustomButton.kt

// Utilidades: NombreUtils.kt
DateUtils.kt
```

### Estructura de Archivos

```kotlin
// 1. Package declaration
package com.sweetmesoft.kmpcontrols.dialogs

// 2. Imports (agrupados y ordenados)
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// 3. Documentación de clase/función
/**
 * Diálogo para selección de fechas.
 * 
 * @param selectedDate Fecha actualmente seleccionada
 * @param onDateSelected Callback cuando se selecciona una fecha
 * @param modifier Modificador para personalización
 */
@Composable
fun DatePickerDialog(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    // Implementación
}
```

### Documentación

#### Funciones Públicas
```kotlin
/**
 * Valida si un email tiene formato correcto.
 * 
 * @param email El email a validar
 * @return true si el email es válido, false en caso contrario
 * 
 * @sample
 * ```kotlin
 * val isValid = StringUtils.isValidEmail("user@example.com") // true
 * val isInvalid = StringUtils.isValidEmail("invalid-email") // false
 * ```
 */
fun isValidEmail(email: String): Boolean
```

#### Composables
```kotlin
/**
 * Control de contraseña con visibilidad toggleable.
 * 
 * @param value Valor actual de la contraseña
 * @param onValueChange Callback cuando cambia el valor
 * @param modifier Modificador para personalización
 * @param label Etiqueta del campo
 * @param enabled Si el campo está habilitado
 * 
 * @sample
 * ```kotlin
 * var password by remember { mutableStateOf("") }
 * PasswordControl(
 *     value = password,
 *     onValueChange = { password = it },
 *     label = "Contraseña"
 * )
 * ```
 */
@Composable
fun PasswordControl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Contraseña",
    enabled: Boolean = true
)
```

### Testing

#### Tests Unitarios
```kotlin
class StringUtilsTest {
    @Test
    fun `isValidEmail should return true for valid emails`() {
        // Given
        val validEmails = listOf(
            "user@example.com",
            "test.email@domain.co.uk",
            "user+tag@example.org"
        )
        
        // When & Then
        validEmails.forEach { email ->
            assertTrue(
                "Email $email should be valid",
                StringUtils.isValidEmail(email)
            )
        }
    }
    
    @Test
    fun `isValidEmail should return false for invalid emails`() {
        // Given
        val invalidEmails = listOf(
            "invalid-email",
            "@example.com",
            "user@",
            ""
        )
        
        // When & Then
        invalidEmails.forEach { email ->
            assertFalse(
                "Email $email should be invalid",
                StringUtils.isValidEmail(email)
            )
        }
    }
}
```

#### Tests de Composables
```kotlin
class PasswordControlTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun `PasswordControl should toggle visibility when icon is clicked`() {
        // Given
        var password by mutableStateOf("")
        
        composeTestRule.setContent {
            PasswordControl(
                value = password,
                onValueChange = { password = it }
            )
        }
        
        // When
        composeTestRule.onNodeWithContentDescription("Toggle password visibility")
            .performClick()
        
        // Then
        composeTestRule.onNodeWithText("test123")
            .assertIsDisplayed()
    }
}
```

## 🔄 Proceso de Pull Request

### Antes de Crear el PR

1. **Asegúrate** de que tu código compila sin errores
2. **Ejecuta** todos los tests
3. **Verifica** que la app de prueba funciona correctamente
4. **Actualiza** la documentación si es necesario
5. **Sincroniza** con la rama principal

### Creando el PR

#### Título
Usa el formato: `tipo: descripción breve`

Tipos:
- `feat`: Nueva característica
- `fix`: Corrección de bug
- `docs`: Cambios en documentación
- `style`: Cambios de formato (no afectan funcionalidad)
- `refactor`: Refactorización de código
- `test`: Agregar o modificar tests
- `chore`: Tareas de mantenimiento

Ejemplos:
- `feat: agregar DateTimePicker component`
- `fix: corregir crash en MapComponent en iOS`
- `docs: actualizar guía de instalación`

#### Descripción
```markdown
## 📝 Descripción
Descripción clara de los cambios realizados.

## 🎯 Tipo de Cambio
- [ ] Bug fix (cambio que corrige un issue)
- [ ] Nueva característica (cambio que agrega funcionalidad)
- [ ] Breaking change (cambio que rompe compatibilidad)
- [ ] Documentación

## 🧪 ¿Cómo se ha probado?
- [ ] Tests unitarios
- [ ] Tests de integración
- [ ] Pruebas manuales en Android
- [ ] Pruebas manuales en iOS
- [ ] Pruebas en kmptestapp

## 📋 Checklist
- [ ] Mi código sigue las convenciones del proyecto
- [ ] He realizado una auto-revisión de mi código
- [ ] He comentado mi código, especialmente en áreas complejas
- [ ] He actualizado la documentación correspondiente
- [ ] Mis cambios no generan nuevas advertencias
- [ ] He agregado tests que prueban mi fix/característica
- [ ] Los tests nuevos y existentes pasan localmente
```

### Revisión del PR

Tu PR será revisado por los mantenedores. Pueden:

1. **Aprobar** y hacer merge
2. **Solicitar cambios** con comentarios específicos
3. **Comentar** sin bloquear el merge

#### Respondiendo a Comentarios
- Lee cuidadosamente todos los comentarios
- Haz los cambios solicitados
- Responde a cada comentario explicando tus cambios
- Marca como resueltos los comentarios que hayas atendido

## 🏷️ Versionado

Seguimos [Semantic Versioning](https://semver.org/):

- **MAJOR** (X.0.0): Cambios incompatibles en la API
- **MINOR** (0.X.0): Nueva funcionalidad compatible
- **PATCH** (0.0.X): Correcciones de bugs compatibles

## 📚 Recursos Adicionales

- [Documentación de Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Guía de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Convenciones de Kotlin](https://kotlinlang.org/docs/coding-conventions.html)
- [Material Design 3](https://m3.material.io/)

## 🙏 Reconocimientos

Todos los contribuidores serán reconocidos en:
- Lista de contribuidores en GitHub
- Archivo AUTHORS.md
- Release notes cuando corresponda

---

¿Tienes preguntas? No dudes en:
- Abrir un [issue de discusión](https://github.com/erickvelasco11/KmpLibrary/issues)
- Contactarnos en [team@sweetmesoft.com](mailto:team@sweetmesoft.com)

¡Gracias por contribuir a SweetMeSoft KMP Library! 🚀