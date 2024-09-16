import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id("module.publication")
}

kotlin {
    //jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    //linuxX64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.settings)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)

            api(libs.moko.permissions)
            api(libs.moko.permissions.compose)
        }
    }
}

android {
    namespace = "com.sweetmesoft.kmplibrary"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}


publishing {
    publications.all {
        this as MavenPublication

        pom {
            name.set(project.name)
            description.set("SweetMeSoft KMP Library")
//            url.set("https://github.com/shepeliev/webrtc-kmp")

//            scm {
//                url.set("https://github.com/shepeliev/webrtc-kmp")
//                connection.set("scm:git:https://github.com/shepeliev/webrtc-kmp.git")
//                developerConnection.set("scm:git:https://github.com/shepeliev/webrtc-kmp.git")
//                tag.set("HEAD")
//            }

//            issueManagement {
//                system.set("GitHub Issues")
//                url.set("https://github.com/shepeliev/webrtc-kmp/issues")
//            }

            developers {
                developer {
                    name.set("Erick Velasco")
                    email.set("erick.velasco@sweetmesoft.com")
                }
            }

            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                    comments.set("A business-friendly OSS license")
                }
            }
        }
    }
}