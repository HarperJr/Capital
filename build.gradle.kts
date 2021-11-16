// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${com.harper.buildsrc.Version.KOTLIN}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}