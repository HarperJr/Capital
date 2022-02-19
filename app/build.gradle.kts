import com.harper.buildsrc.Version
import com.harper.buildsrc.accompanist
import com.harper.buildsrc.android
import com.harper.buildsrc.capitalAppDefaultConfig
import com.harper.buildsrc.cicerone
import com.harper.buildsrc.compose
import com.harper.buildsrc.composeConstraintLayout
import com.harper.buildsrc.core
import com.harper.buildsrc.database
import com.harper.buildsrc.koin
import com.harper.buildsrc.kotlin
import com.harper.buildsrc.repository
import com.harper.buildsrc.shelter
import com.harper.buildsrc.spec
import com.harper.buildsrc.timber

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\HarperJr\\Desktop\\InDev\\Capital\\capitalkeystore")
            storePassword = "j2HsoxA952Fk"
            keyAlias = "capital"
            keyPassword = "j2HsoxA952Fk"
        }
    }
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

detekt {
    buildUponDefaultConfig = false
    allRules = false
    config = files("$projectDir/config/detekt.yml")
    baseline = file("$projectDir/config/baseline.xml")
    source = files("src/main/java", "src/main/kotlin")
}

dependencies {
    kotlin()
    android()
    compose()
    accompanist()
    composeConstraintLayout()
    cicerone()
    koin()
    timber()

    core()
    database()
    repository()
    spec()
    shelter()
}