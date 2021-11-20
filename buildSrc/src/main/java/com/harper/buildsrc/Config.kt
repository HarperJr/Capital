package com.harper.buildsrc

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.LibraryDefaultConfig
import org.gradle.api.JavaVersion
import java.io.File

fun BaseAppModuleExtension.capitalAppDefaultConfig(configClosure: ApplicationDefaultConfig.() -> Unit) {
    compileSdkVersion = Version.Application.compileSdkVersion
    buildToolsVersion = Version.Application.buildTools

    defaultConfig {
        applicationId = "com.harper.capital"
        minSdk = Version.Application.minSdk
        targetSdk = Version.Application.targetSdk
        versionCode = Version.Application.versionCode
        versionName = Version.Application.versionName

        configClosure.invoke(this)
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
}

fun LibraryExtension.capitalLibDefaultConfig(configClosure: LibraryDefaultConfig.() -> Unit) {
    compileSdk = Version.Application.compileSdk

    defaultConfig {
        minSdk = Version.Application.minSdk
        targetSdk = Version.Application.targetSdk

        configClosure.invoke(this)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(File("proguard-rules.pro"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
