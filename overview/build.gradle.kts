import com.harper.buildsrc.Version
import com.harper.buildsrc.accompanist
import com.harper.buildsrc.android
import com.harper.buildsrc.capitalLibBuildFeatures
import com.harper.buildsrc.capitalLibDefaultConfig
import com.harper.buildsrc.compose
import com.harper.buildsrc.composeConstraintLayout
import com.harper.buildsrc.core
import com.harper.buildsrc.koin
import com.harper.buildsrc.kotlin
import com.harper.buildsrc.spec
import com.harper.buildsrc.timber

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
    composeConstraintLayout()
    accompanist()
    koin()

    core()
    spec()
    timber()
}