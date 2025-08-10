# 🤝 Contributing Guide

Thank you for your interest in contributing to SweetMeSoft KMP Library! This guide will help you understand how you can participate in the development of the library.

## 📋 Table of Contents

- [Code of Conduct](#-code-of-conduct)
- [How to Contribute?](#-how-to-contribute)
- [Reporting Issues](#-reporting-issues)
- [Feature Requests](#-feature-requests)
- [Contributing Code](#-contributing-code)
- [Code Standards](#-code-standards)
- [Pull Request Process](#-pull-request-process)
- [Environment Setup](#-environment-setup)

## 📜 Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to [team@sweetmesoft.com](mailto:team@sweetmesoft.com).

### Our Commitments

- Use welcoming and inclusive language
- Respect different viewpoints and experiences
- Accept constructive criticism gracefully
- Focus on what is best for the community
- Show empathy towards other community members

## 🚀 How to Contribute?

There are many ways to contribute to SweetMeSoft KMP Library:

### 🐛 Report Bugs
- First search in [existing issues](https://github.com/erickvelasco11/KmpLibrary/issues)
- If you don't find a similar issue, create a new one
- Provide detailed information about the problem

### 💡 Suggest Improvements
- Review [planned features](https://github.com/erickvelasco11/KmpLibrary/projects)
- Open an issue with the "enhancement" label
- Clearly describe the proposed improvement

### 📝 Improve Documentation
- Fix typos
- Improve existing explanations
- Add usage examples
- Translate documentation

### 💻 Contribute Code
- Implement new features
- Fix existing bugs
- Improve performance
- Add tests

## 🐛 Reporting Issues

When reporting a bug, include:

### Environment Information
```
- Library version: [e.g. 1.6.6]
- Platform: [Android/iOS/Desktop]
- OS version: [e.g. Android 14, iOS 17.0]
- Kotlin version: [e.g. 2.1.0]
- Compose version: [e.g. 1.7.5]
```

### Problem Description
- **Summary:** Brief description of the problem
- **Steps to reproduce:** Detailed list of steps
- **Expected behavior:** What should happen
- **Actual behavior:** What is happening
- **Screenshots:** If applicable
- **Logs/Errors:** Relevant error messages

### Issue Template
```markdown
## 🐛 Bug Description
Clear and concise description of the problem.

## 🔄 Steps to Reproduce
1. Go to '...'
2. Click on '...'
3. Scroll to '...'
4. See error

## ✅ Expected Behavior
Description of what you expected to happen.

## ❌ Actual Behavior
Description of what actually happened.

## 📱 Environment
- Library version: 
- Platform: 
- OS version: 
- Device: 

## 📎 Additional Information
Any other relevant information about the problem.
```

## 💡 Feature Requests

To request a new feature:

1. **Verify** that a similar request doesn't already exist
2. **Describe** the problem the feature solves
3. **Explain** the proposed solution
4. **Consider** alternatives you have evaluated
5. **Provide** usage examples

### Feature Request Template
```markdown
## 🚀 Feature Description
Clear description of the requested feature.

## 🎯 Problem it Solves
What specific problem does this feature solve?

## 💡 Proposed Solution
Detailed description of how it should work.

## 🔄 Alternatives Considered
What other solutions have you considered?

## 📝 Usage Examples
```kotlin
// Example of how the feature would be used
```

## 📊 Impact
- Which modules would be affected?
- Is it a breaking change?
- Does it require migration?
```

## 💻 Contributing Code

### Environment Setup

#### Requirements
- **JDK 17** or higher
- **Android Studio** (latest stable version)
- **Xcode** (for iOS development)
- **Git**

#### Initial Setup
```bash
# 1. Fork the repository
# 2. Clone your fork
git clone https://github.com/YOUR_USERNAME/KmpLibrary.git
cd KmpLibrary

# 3. Add the original repository as upstream
git remote add upstream https://github.com/erickvelasco11/KmpLibrary.git

# 4. Create a branch for your feature
git checkout -b feature/my-new-feature

# 5. Install dependencies
./gradlew build
```

### Project Structure
```
KmpLibrary/
├── kmpcontrols/          # Basic UI components
├── kmpmaps/              # Maps integration
├── library/              # Main module
├── kmptestapp/           # Test application
├── docs/                 # Documentation
└── .github/              # GitHub configuration
```

### Development Workflow

1. **Sync** your fork with upstream
```bash
git fetch upstream
git checkout main
git merge upstream/main
```

2. **Create** a branch for your feature
```bash
git checkout -b feature/brief-description
```

3. **Develop** your feature
   - Write clean and well-documented code
   - Add tests for new functionality
   - Update documentation if necessary

4. **Test** your code
```bash
# Run tests
./gradlew test

# Verify that the test app works
./gradlew :kmptestapp:installDebug
```

5. **Commit** your changes
```bash
git add .
git commit -m "feat: add new feature X"
```

6. **Push** to your fork
```bash
git push origin feature/brief-description
```

7. **Create** a Pull Request

## 📏 Code Standards

### Naming Conventions

#### Kotlin
```kotlin
// Classes: PascalCase
class DatePickerDialog

// Functions and variables: camelCase
fun showDatePicker()
val selectedDate = LocalDate.now()

// Constants: SCREAMING_SNAKE_CASE
const val DEFAULT_TIMEOUT = 5000

// Composables: PascalCase
@Composable
fun CustomButton() { }
```

#### Files
```
// Classes: ClassName.kt
DatePickerDialog.kt

// Composables: ComposableName.kt
CustomButton.kt

// Utilities: NameUtils.kt
DateUtils.kt
```

### File Structure

```kotlin
// 1. Package declaration
package com.sweetmesoft.kmpcontrols.dialogs

// 2. Imports (grouped and ordered)
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// 3. Class/function documentation
/**
 * Dialog for date selection.
 * 
 * @param selectedDate Currently selected date
 * @param onDateSelected Callback when a date is selected
 * @param modifier Modifier for customization
 */
@Composable
fun DatePickerDialog(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    // Implementation
}
```

### Documentation

#### Public Functions
```kotlin
/**
 * Validates if an email has correct format.
 * 
 * @param email The email to validate
 * @return true if the email is valid, false otherwise
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
 * Password control with toggleable visibility.
 * 
 * @param value Current password value
 * @param onValueChange Callback when value changes
 * @param modifier Modifier for customization
 * @param label Field label
 * @param enabled Whether the field is enabled
 * 
 * @sample
 * ```kotlin
 * var password by remember { mutableStateOf("") }
 * PasswordControl(
 *     value = password,
 *     onValueChange = { password = it },
 *     label = "Password"
 * )
 * ```
 */
@Composable
fun PasswordControl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    enabled: Boolean = true
)
```

### Testing

#### Unit Tests
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

#### Composable Tests
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

## 🔄 Pull Request Process

### Before Creating the PR

1. **Ensure** your code compiles without errors
2. **Run** all tests
3. **Verify** that the test app works correctly
4. **Update** documentation if necessary
5. **Sync** with the main branch

### Creating the PR

#### Title
Use the format: `type: brief description`

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Format changes (don't affect functionality)
- `refactor`: Code refactoring
- `test`: Add or modify tests
- `chore`: Maintenance tasks

Examples:
- `feat: add DateTimePicker component`
- `fix: fix crash in MapComponent on iOS`
- `docs: update installation guide`

#### Description
```markdown
## 📝 Description
Clear description of the changes made.

## 🎯 Type of Change
- [ ] Bug fix (change that fixes an issue)
- [ ] New feature (change that adds functionality)
- [ ] Breaking change (change that breaks compatibility)
- [ ] Documentation

## 🧪 How has it been tested?
- [ ] Unit tests
- [ ] Integration tests
- [ ] Manual testing on Android
- [ ] Manual testing on iOS
- [ ] Testing in kmptestapp

## 📋 Checklist
- [ ] My code follows the project conventions
- [ ] I have performed a self-review of my code
- [ ] I have commented my code, especially in complex areas
- [ ] I have updated the corresponding documentation
- [ ] My changes don't generate new warnings
- [ ] I have added tests that prove my fix/feature
- [ ] New and existing tests pass locally
```

### PR Review

Your PR will be reviewed by maintainers. They can:

1. **Approve** and merge
2. **Request changes** with specific comments
3. **Comment** without blocking the merge

#### Responding to Comments
- Read all comments carefully
- Make the requested changes
- Respond to each comment explaining your changes
- Mark as resolved the comments you have addressed

## 🏷️ Versioning

We follow [Semantic Versioning](https://semver.org/):

- **MAJOR** (X.0.0): Incompatible API changes
- **MINOR** (0.X.0): Compatible new functionality
- **PATCH** (0.0.X): Compatible bug fixes

## 📚 Additional Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Jetpack Compose Guide](https://developer.android.com/jetpack/compose)
- [Kotlin Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Material Design 3](https://m3.material.io/)

## 🙏 Acknowledgments

All contributors will be recognized in:
- GitHub contributors list
- AUTHORS.md file
- Release notes when applicable

---

Have questions? Feel free to:
- Open a [discussion issue](https://github.com/erickvelasco11/KmpLibrary/issues)
- Contact us at [team@sweetmesoft.com](mailto:team@sweetmesoft.com)

Thank you for contributing to SweetMeSoft KMP Library! 🚀