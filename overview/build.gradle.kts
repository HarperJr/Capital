import com.harper.buildsrc.*

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
    android()
    compose()
    composeConstraintLayout()
    koin()

    core()
    spec()
}