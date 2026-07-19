---
trigger: always_on
---

# Documentation Sync Rules

Keep docs/ (including `SKILL.md`) sync with config, capabilities, and API signatures.

---

## 1. Version Catalog Alignment

Build configs update → sync docs (`index.md`, `kmpcontrols.md`, `kmpmaps.md`, `library.md`, `getting-started.md`):
- **Kotlin**: Sync `Kotlin` refs with `kotlin` in `libs.versions.toml`.
- **Compose**: Sync `Compose Multiplatform` with `compose-plugin` catalog version.
- **Android**: Align `Min SDK` and `Target SDK` with catalog variables.
- **iOS**: Sync iOS target versions with project configs.

---

## 2. API Signature & Capability Integrity

- **Exact Code Blocks**: Examples (`DatePicker`, `PasswordControl`, `MapComponent`, `AutoCompleteTextField`) must match actual signatures, parameters, imports.
- **Params**: Sync API reference types/returns with active classes.
- **SKILL.md Sync**: Keep `SKILL.md` synchronized with current module capabilities, architectural rules, and documentation indexes.

---

## 3. Structural Frontmatter

All pages in `docs/` must keep Jekyll headers:
```markdown
---
layout: default
title: [Title]
nav_order: [Order]
---
```
Do not delete layouts. Exception: `SKILL.md` uses custom YAML frontmatter header.

---

## 4. Tone and Formatting

- **Tone**: Technical, clear English.
- **No Emojis**: Emojis forbidden.
- **Anchors**: Table of Contents links must map to correct headings.