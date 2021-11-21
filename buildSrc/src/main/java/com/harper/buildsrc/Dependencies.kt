package com.harper.buildsrc

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.core() {
    project(":core")
}

fun DependencyHandlerScope.overview() {
    project(":overview")
}

fun DependencyHandlerScope.android() {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}")
    implementation("androidx.core:core-ktx:${Version.Library.ktx}")
    implementation("androidx.appcompat:appcompat:${Version.Library.appCompat}")
    implementation("com.google.android.material:material:${Version.Library.material}")
}

fun DependencyHandlerScope.cicerone() = implementation("com.github.terrakok:cicerone:7.1")

fun DependencyHandlerScope.timber() = implementation("com.jakewharton.timber:timber:5.0.1")

fun DependencyHandlerScope.coil() = implementation("io.coil-kt:coil-compose:1.4.0")

fun DependencyHandlerScope.koin() {
    implementation("io.insert-koin:koin-android:${Version.Library.koin}")
    implementation("io.insert-koin:koin-androidx-compose:${Version.Library.koin}")
}

fun DependencyHandlerScope.retrofit() {
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")

}

fun DependencyHandlerScope.room() {
    implementation("androidx.room:room-runtime:${Version.Library.room}")
    implementation("androidx.room:room-ktx:${Version.Library.room}")
    kapt("androidx.room:room-compiler:${Version.Library.room}")
}

fun DependencyHandlerScope.compose() {
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.runtime:runtime:${Version.Library.compose}")
    implementation("androidx.compose.runtime:runtime-livedata:${Version.Library.compose}")
    implementation("androidx.compose.material:material:${Version.Library.compose}")
    implementation("androidx.compose.foundation:foundation:${Version.Library.compose}")
    implementation("androidx.compose.foundation:foundation-layout:${Version.Library.compose}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Version.Library.compose}")
    implementation("androidx.compose.animation:animation:${Version.Library.compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
}

fun DependencyHandlerScope.implementation(module: String) = add("implementation", module)

fun DependencyHandlerScope.debugImplementation(module: String) = add("debugImplementation", module)

fun DependencyHandlerScope.kapt(module: String) = add("kapt", module)

fun DependencyHandlerScope.project(path: String) = add("implementation", (project(mapOf("path" to path))))
