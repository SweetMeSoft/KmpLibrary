---
trigger: always
---

# Project Documentation Synchronization Guidelines

This document defines the structural, versioning, and content maintenance standards for all documentation files located under the `docs/` directory. These rules are always applied to ensure documentation remains accurate whenever project configurations or API signatures change.

---

## 1. Required Version Alignment

Whenever build parameters, target SDK platforms, or toolchain configurations are updated in the build files (`gradle/libs.versions.toml`, `build.gradle.kts`, or `settings.gradle.kts`), they must be instantly updated in all relevant documentation pages:

- **Kotlin Compiler**: Sync all `Kotlin` references in documentation (`index.md`, `kmpcontrols.md`, `kmpmaps.md`, `library.md`) with the `kotlin` version declared in `libs.versions.toml`.
- **Compose Multiplatform**: Sync `Compose Multiplatform` versions in documentation with the `compose-plugin` version in the catalog.
- **Android SDK Levels**: Align the Android `Min SDK` and `Target SDK` parameters in documentation with the `android-minSdk` and `android-targetSdk` references.
- **iOS Support Target**: Align iOS deployment targets with the active platform configuration settings.

---

## 2. API Reference & Signature Integrity

- **Accurate Code Blocks**: All code examples in documentation illustrating component declarations (such as `DatePicker`, `PasswordControl`, `MapComponent`) must match the actual function signatures, imports, and parameters in the code.
- **Reference Parameters**: Verify that parameters and return types detailed in the API Reference sections are fully synchronized with active class declarations.

---

## 3. Structural Frontmatter Specifications

Every documentation page in the `docs/` folder must preserve Jekyll header definitions at the very beginning of the file:
```markdown
---
layout: default
title: [Page Title]
nav_order: [Order Number]
---
```
Do not modify or remove Jekyll layout settings unless restructuring site navigation.

---

## 4. Tone and Formatting

- **Technical Tone**: Maintain professional, clear, and objective English across all files.
- **Emoji-free**: The use of emojis is strictly prohibited in any documentation page.
- **Anchor Verification**: Verify that all Table of Contents anchors map directly to existing, correct headings.