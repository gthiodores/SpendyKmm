plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply(true)
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-8" apply(true)
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")
        pod("KMPNativeCoroutinesAsync", "1.0.0-ALPHA-8")
        pod("KMPNativeCoroutinesCombine", "1.0.0-ALPHA-8")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        val commonMain by getting {
            dependencies {
                api("com.russhwolf:multiplatform-settings:1.0.0")
                api("com.russhwolf:multiplatform-settings-coroutines:1.0.0")
                api("com.arkivanov.mvikotlin:mvikotlin:3.2.0")
                api("com.arkivanov.mvikotlin:mvikotlin-main:3.2.0")
                api("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.2.0")
                api("com.arkivanov.mvikotlin:rx:3.2.0")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                api("io.insert-koin:koin-core:3.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.russhwolf:multiplatform-settings-test:1.0.0")
                implementation("io.insert-koin:koin-test:3.4.0")
            }
        }
        val androidMain by getting
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.gthio.spendykmm"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}