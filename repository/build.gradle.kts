import com.harper.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.harper.capital.repository"
    capitalLibDefaultConfig { }

    buildFeatures {
        buildConfig = false
        viewBinding = false
        compose = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    kotlinOptions {
        jvmTarget = Version.jvmTarget
    }
}

dependencies {
    kotlin()
    koin()

    network()
    database()
    spec()
    core()
}