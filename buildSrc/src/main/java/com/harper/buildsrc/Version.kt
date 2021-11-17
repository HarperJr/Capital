package com.harper.buildsrc

object Version {
    const val JVM_TARGET = "1.8"
    const val KOTLIN = "1.5.30"

    object Application {
        const val MIN_SDK = 27
        const val TARGET_SDK = 30
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0"
        const val COMPILE_SDK = "android-31"
        const val BUILD_TOOLS = "30.0.3"
    }

    object Library {
        const val KTX = "1.7.0"
        const val APP_COMPAT = "1.3.1"
        const val MATERIAL = "1.4.0"
        const val COMPOSE = "1.0.3"
        const val KOIN = "3.1.2"
        const val ROOM = "2.3.0"
    }
}