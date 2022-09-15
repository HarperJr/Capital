import com.harper.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.harper.core"
    capitalLibDefaultConfig { }

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
    timber()
}