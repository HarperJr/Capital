import com.harper.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.harper.capital.network"
    capitalLibDefaultConfig {
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
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")

    kotlin()
    koin()
    core()
    gson()
    retrofit()
    timber()
}