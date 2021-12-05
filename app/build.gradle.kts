import com.harper.buildsrc.Version
import com.harper.buildsrc.android
import com.harper.buildsrc.capitalAppDefaultConfig
import com.harper.buildsrc.cicerone
import com.harper.buildsrc.compose
import com.harper.buildsrc.core
import com.harper.buildsrc.database
import com.harper.buildsrc.koin
import com.harper.buildsrc.kotlin
import com.harper.buildsrc.overview
import com.harper.buildsrc.repository
import com.harper.buildsrc.timber

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    capitalAppDefaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    kotlinOptions {
        jvmTarget = Version.jvmTarget
    }
}

dependencies {
    kotlin()
    android()
    compose()
    cicerone()
    koin()
    timber()

    overview()
    database()
    repository()
    core()
}