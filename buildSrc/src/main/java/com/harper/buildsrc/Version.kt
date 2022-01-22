package com.harper.buildsrc

object Version {
    const val jvmTarget = "1.8"
    const val kotlin = "1.5.31"
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
        const val appCompat = "1.3.1"
        const val material = "1.4.0"
        const val compose = "1.0.5"
        const val accompanist = "0.20.3"
        const val koin = "3.1.2"
        const val room = "2.3.0"
        const val ktLint = "0.38.0"
    }
}