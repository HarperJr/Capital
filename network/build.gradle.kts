import com.harper.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    capitalLibDefaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "EXCHANGE_URL", "\"http://api.exchangeratesapi.io/v1/\"")
        buildConfigField("String", "EXCHANGE_API_KEY", "\"271a48b04c0a86f10e877d69322bc958\"")
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
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.20")

    kotlin()
    koin()
    core()
    gson()
    retrofit()
    timber()
}