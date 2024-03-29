import com.harper.buildsrc.Version
import com.harper.buildsrc.capitalLibDefaultConfig
import com.harper.buildsrc.core
import com.harper.buildsrc.kotlin

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.harper.capital.domain"
    capitalLibDefaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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

    core()
}