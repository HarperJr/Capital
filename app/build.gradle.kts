import com.harper.buildsrc.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion = Version.Application.COMPILE_SDK
    buildToolsVersion = Version.Application.BUILD_TOOLS

    defaultConfig {
        applicationId = "com.harper.capital"
        minSdk = Version.Application.MIN_SDK
        targetSdk = Version.Application.TARGET_SDK
        versionCode = Version.Application.VERSION_CODE
        versionName = Version.Application.VERSION_NAME

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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                File("proguard-rules.pro")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Version.JVM_TARGET
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.Library.COMPOSE
        kotlinCompilerVersion = Version.KOTLIN
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
}