pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.60.5"
}

rootProject.name = "kmplibrary"
include(":library", ":kmpmaps", ":kmptestapp", ":kmpcontrols")