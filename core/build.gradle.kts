import com.harper.buildsrc.android
import com.harper.buildsrc.capitalLibDefaultConfig
import com.harper.buildsrc.compose
import com.harper.buildsrc.koin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    capitalLibDefaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    android()
    compose()
    koin()
}