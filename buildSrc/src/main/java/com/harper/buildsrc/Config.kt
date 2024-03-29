package com.harper.buildsrc

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.LibraryDefaultConfig
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.get
import java.io.File

fun BaseAppModuleExtension.capitalAppDefaultConfig(configClosure: ApplicationDefaultConfig.() -> Unit) {
    compileSdkVersion = Version.App.compileSdkVersion
    buildToolsVersion = Version.App.buildTools

    defaultConfig {
        applicationId = "com.harper.capital"
        minSdk = Version.App.minSdk
        targetSdk = Version.App.targetSdk
        versionCode = Version.App.versionCode
        versionName = Version.App.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        configClosure.invoke(this)
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            manifestPlaceholders += "app_name" to "Capital-debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            signingConfig = signingConfigs["release"]
            manifestPlaceholders += "app_name" to "Capital"
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

    composeOptions {
        kotlinCompilerExtensionVersion = Version.Library.composeCompiler
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        resources.excludes += "/META-INF/AL2.0"
        resources.excludes += "/META-INF/LGPL2.1"
    }
}

fun LibraryExtension.capitalLibDefaultConfig(configClosure: LibraryDefaultConfig.() -> Unit) {
    compileSdk = Version.App.compileSdk
    buildToolsVersion = Version.App.buildTools

    defaultConfig {
        minSdk = Version.App.minSdk
        targetSdk = Version.App.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        configClosure.invoke(this)
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(File("proguard-rules.pro"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.Library.composeCompiler
    }

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        resources.excludes += "/META-INF/AL2.0"
        resources.excludes += "/META-INF/LGPL2.1"
    }
}

fun LibraryExtension.capitalLibBuildFeatures() {
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
}
