import com.harper.buildsrc.Version
import com.harper.buildsrc.accompanist
import com.harper.buildsrc.android
import com.harper.buildsrc.capitalLibBuildFeatures
import com.harper.buildsrc.capitalLibDefaultConfig
import com.harper.buildsrc.compose
import com.harper.buildsrc.koin
import com.harper.buildsrc.kotlin

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

    capitalLibBuildFeatures()

    kotlinOptions {
        jvmTarget = Version.jvmTarget
    }
}

dependencies {
    kotlin()
    android()
    compose()
    accompanist()
    koin()
}