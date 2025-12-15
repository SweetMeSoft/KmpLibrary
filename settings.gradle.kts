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
    id("de.fayard.refreshVersions") version "0.60.6"
}

rootProject.name = "kmplibrary"
include(":kmpbase", ":kmpmaps", ":kmptestapp", ":kmpcontrols")