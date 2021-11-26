import com.harper.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    capitalLibDefaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("int", "DATABASE_VERSION", "1")
        buildConfigField("String", "DATABASE_NAME", "\"capital_db\"")
    }

    kotlinOptions {
        jvmTarget = Version.jvmTarget
    }

    buildFeatures {
        viewBinding = false
        compose = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
}

dependencies {
    koin()
    core()
    room()
    gson()
}