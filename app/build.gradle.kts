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

    composeOptions {
        kotlinCompilerExtensionVersion = Version.Library.compose
        kotlinCompilerVersion = Version.kotlin
    }

    buildFeatures {
        compose = true
    }

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        resources.excludes += "/META-INF/AL2.0"
        resources.excludes += "/META-INF/LGPL2.1"
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

    // modules
    core()
}