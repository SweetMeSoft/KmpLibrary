import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    id("com.vanniktech.maven.publish") version "0.31.0"
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
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.maps.compose)
            implementation(libs.permissions.location)
            implementation(project(":library"))
            implementation(project(":kmpcontrols"))

            api(libs.moko.permissions)
            api(libs.moko.permissions.compose)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.play.services.location)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.sweetmesoft.kmpmaps"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    coordinates(
        groupId = "com.sweetmesoft.kmpmaps",
        artifactId = "kmpmaps",
        version = libs.versions.sweetmesoft.get()
    )

    pom {
        name.set(project.name)
        description.set("SweetMeSoft KMP Maps")
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

        scm {
            url.set("https://github.com/erickvelasco11/KmpLibrary")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

compose.resources {
    publicResClass = true
    generateResClass = auto
}