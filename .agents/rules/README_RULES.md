---
trigger: manual
---

# Project README Creation Guidelines

This document defines the structural and content standards for all README files within the workspace. Adherence to these guidelines ensures consistent, technical, and maintainable project documentation.

---

## 1. Required Structural Elements

Every project README must start with the following elements in order:

### Technology Badges
Technology badges must be placed at the very top of the document. Use the shields.io badge format linking to official documentation.
Example:
```markdown
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg)](https://kotlinlang.org/docs/multiplatform.html)
```

### Table of Contents
A table of contents with anchors to all primary headers must follow the technology badges to ensure easy navigation.

---

## 2. Required Document Sections

The body of the README must be structured into the following distinct sections:

### Project Summary
A concise description of the project, explaining its business purpose, scope, and target audience.

### Functionalities
A list of the core features and operations executed by the project. This section must describe what the application does at a functional level.

### Libraries and Dependencies
A structured table or list detailing the third-party libraries, SDKs, and platform frameworks utilized, along with their purpose.

### Core Implementation
A high-level technical description of the application runtime, compilation pipelines, or executing environments.

### Versions
A clear statement of the runtime versions, SDK targets, build tools, and API versions supported by the project.

### Folder Structure
A text-based tree diagram illustrating the physical directories of the project, annotated with brief explanations of what each folder contains.

### Design Patterns Implemented
A description of the software design patterns and architectural boundaries enforced in the codebase (e.g., MVVM, Repository Pattern, Dependency Inversion, Clean Architecture).

### Configurations
Step-by-step instructions on environment setups, configuration files (e.g., appsettings.json, config.json), and environment variable requirements.

### Integrations
A description of third-party APIs, external cloud resources, databases, and platform integrations connected to the project.

---

## 3. Content Exclusions and Prohibitions

To ensure the documentation remains concise, maintainable, and up-to-date, the following elements are prohibited:

### Avoid Code Examples
Do not include code snippets, class structures, implementation examples, or logging utility blocks in the README files. Code usage should be inferred from interfaces or standard documentation.

### Avoid App Status
Do not document temporary application states, checklist items showing feature completion, development progress metrics, or unresolved issue lists. The README must describe the project as a fully-realized system.

### Avoid Additional Functionalities
Do not include speculative feature backlogs, planned roadmap items, or auxiliary features that are not part of the active core codebase.

---

## 4. Tone and Formatting

- **Language**: All documentation must be written in professional, technical English.
- **Emoji-free**: The use of emojis is strictly prohibited in headers, lists, and body copy.
- **Markdown Consistency**: Use standard GitHub Flavored Markdown styling, tables, and lists.
