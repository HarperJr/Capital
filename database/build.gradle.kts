import com.harper.buildsrc.Version
import com.harper.buildsrc.capitalLibDefaultConfig
import com.harper.buildsrc.core
import com.harper.buildsrc.gson
import com.harper.buildsrc.koin
import com.harper.buildsrc.kotlin
import com.harper.buildsrc.room

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    capitalLibDefaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("int", "DATABASE_VERSION", "2")
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
    kotlin()
    koin()
    core()
    room()
    gson()
}