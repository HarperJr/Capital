// Top-level build file where you can add configuration options common to all sub-projects/modules.

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0")
    }
}

detekt {
    buildUponDefaultConfig = false
    allRules = false
    config = files("$projectDir/config/detekt.yml")
    baseline = file("$projectDir/config/baseline.xml")
    source = files("src/main/java", "src/main/kotlin")
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
