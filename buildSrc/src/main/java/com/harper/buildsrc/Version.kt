package com.harper.buildsrc

object Version {
    const val jvmTarget = "1.8"
    const val kotlin = "1.6.21"
    const val detekt = "1.19.0"

    object App {
        const val minSdk = 27
        const val targetSdk = 31
        const val versionCode = 1
        const val versionName = "1.0"
        const val compileSdkVersion = "android-31"
        const val compileSdk = 31
        const val buildTools = "30.0.3"
    }

    object Library {
        const val ktx = "1.7.0"
        const val coroutines = "1.6.0-RC"
        const val appCompat = "1.6.0-alpha04"
        const val material = "1.4.0"
        const val compose = "1.2.0-beta02"
        const val accompanist = "0.24.9-beta"
        const val koin = "3.2.0"
        const val room = "2.4.1"
        const val dataStore = "1.0.0"
    }

    object Test {
        const val core = "1.4.0"
        const val runner = "1.4.0"
        const val junit = "1.1.3"
        const val rules = "1.4.0"
        const val truth = "1.4.0"
        const val mockk = "1.9.3"
        const val mockkAndroid = "1.12.4"
        const val coroutines = "1.6.1"
        const val mockito = "4.5.1"
        const val mockitoKotlin = "4.0.0"
    }
}