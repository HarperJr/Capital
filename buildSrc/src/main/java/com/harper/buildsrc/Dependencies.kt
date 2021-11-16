package com.harper.buildsrc

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.android() {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN}")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
}

fun DependencyHandlerScope.cicerone() = implementation("com.github.terrakok:cicerone:7.1")

fun DependencyHandlerScope.timber() = implementation("com.jakewharton.timber:timber:5.0.1")

fun DependencyHandlerScope.coil() = implementation("io.coil-kt:coil-compose:1.4.0")


fun DependencyHandlerScope.koin() {
    implementation("io.insert-koin:koin-android:${Version.Library.KOIN}")
    implementation("io.insert-koin:koin-androidx-compose:${Version.Library.KOIN}")
}

fun DependencyHandlerScope.retrofit() {
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")

}

fun DependencyHandlerScope.room() {
    implementation("androidx.room:room-runtime:${Version.Library.ROOM}")
    implementation("androidx.room:room-ktx:${Version.Library.ROOM}")
    kapt("androidx.room:room-compiler:${Version.Library.ROOM}")
}

fun DependencyHandlerScope.compose() {
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.runtime:runtime:${Version.Library.COMPOSE}")
    implementation("androidx.compose.runtime:runtime-livedata:${Version.Library.COMPOSE}")
    implementation("androidx.compose.material:material:${Version.Library.COMPOSE}")
    implementation("androidx.compose.foundation:foundation:${Version.Library.COMPOSE}")
    implementation("androidx.compose.foundation:foundation-layout:${Version.Library.COMPOSE}")
    implementation("androidx.compose.ui:ui-tooling:${Version.Library.COMPOSE}")
    implementation("androidx.compose.animation:animation:${Version.Library.COMPOSE}")
    implementation("androidx.compose.ui:ui-test-junit4:${Version.Library.COMPOSE}")
    implementation("androidx.compose.ui:ui-test-manifest:${Version.Library.COMPOSE}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-rc01")
}

fun DependencyHandlerScope.implementation(module: String) = add("implementation", module)

fun DependencyHandlerScope.kapt(module: String) = add("kapt", module)
