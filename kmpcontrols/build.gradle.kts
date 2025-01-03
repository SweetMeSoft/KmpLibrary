import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    id("com.vanniktech.maven.publish") version "0.30.0"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
        publishLibraryVariants("release")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.compose.icons)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlin.serialization)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        iosMain.dependencies {
        }
    }
}

android {
    namespace = "com.sweetmesoft.kmpcontrols"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

mavenPublishing {
    coordinates(
        groupId = "com.sweetmesoft.kmpcontrols",
        artifactId = "kmpcontrols",
        version = libs.versions.sweetmesoft.get()
    )

    pom {
        name.set(project.name)
        description.set("SweetMeSoft KMP Controls")
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