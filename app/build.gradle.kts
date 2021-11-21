import com.harper.buildsrc.*

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
    android()
    compose()
    retrofit()
    cicerone()
    koin()
    room()
    coil()
    timber()

    overview()
    core()
}