import com.harper.buildsrc.accompanist
import com.harper.buildsrc.android
import com.harper.buildsrc.capitalLibDefaultConfig
import com.harper.buildsrc.cicerone
import com.harper.buildsrc.compose
import com.harper.buildsrc.composeConstraintLayout
import com.harper.buildsrc.core
import com.harper.buildsrc.database
import com.harper.buildsrc.koin
import com.harper.buildsrc.kotlin
import com.harper.buildsrc.repository
import com.harper.buildsrc.spec
import com.harper.buildsrc.timber

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    capitalLibDefaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    kotlinOptions {
        jvmTarget = com.harper.buildsrc.Version.jvmTarget
    }
}

dependencies {
    kotlin()
    android()
    compose()
    accompanist()
    composeConstraintLayout()
    koin()
    timber()

    core()
    database()
    repository()
    spec()
}