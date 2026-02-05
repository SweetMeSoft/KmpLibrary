import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    id("com.vanniktech.maven.publish") version "0.34.0"
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("kotlin.time.ExperimentalTime")
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.material3)
            implementation(libs.components.resources)
            implementation(libs.compottie)
            implementation(libs.foundation)
            implementation(libs.imagepickerkmp)
            implementation(libs.kamel.image.default)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.material.kolor)
            implementation(libs.runtime)
            implementation(libs.settings)
            implementation(libs.tabler.icons.kmp)
            implementation(libs.ui)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)

            api(libs.moko.permissions)
            api(libs.moko.permissions.compose)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.sweetmesoft.kmpbase"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    coordinates(
        groupId = "com.sweetmesoft.kmpbase",
        artifactId = "kmpbase",
        version = libs.versions.sweetmesoft.get()
    )

    pom {
        name.set(project.name)
        description.set("SweetMeSoft KMP Library")
        inceptionYear.set("2024")
        url.set("https://github.com/erickvelasco11/KmpLibrary")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                name.set("Erick Velasco")
                email.set("erick.velasco@sweetmesoft.com")
            }
        }

        scm { url.set("https://github.com/erickvelasco11/KmpLibrary") }
    }

    publishToMavenCentral()
    signAllPublications()
}

compose.resources {
    publicResClass = true
    generateResClass = auto
}
