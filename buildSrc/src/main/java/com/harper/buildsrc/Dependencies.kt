package com.harper.buildsrc

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.shelter() {
    project(":shelter")
}

fun DependencyHandlerScope.core() {
    project(":core")
}

fun DependencyHandlerScope.database() {
    project(":database")
}

fun DependencyHandlerScope.network() {
    project(":network")
}

fun DependencyHandlerScope.spec() {
    project(":spec")
}

fun DependencyHandlerScope.repository() {
    project(":repository")
}

fun DependencyHandlerScope.kotlin() {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.Library.coroutines}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}")
}

fun DependencyHandlerScope.android() {
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

fun DependencyHandlerScope.gson() {
    implementation("com.google.code.gson:gson:2.8.6")
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
    implementation("androidx.activity:activity-compose:1.6.0-rc02")
    implementation("androidx.compose.runtime:runtime:${Version.Library.compose}")
    implementation("androidx.compose.runtime:runtime-livedata:${Version.Library.compose}")
    implementation("androidx.compose.material:material:${Version.Library.compose}")
    implementation("androidx.compose.material:material-icons-extended:${Version.Library.compose}")
    implementation("androidx.compose.material:material-icons-core:${Version.Library.compose}")
    implementation("androidx.compose.foundation:foundation:${Version.Library.compose}")
    implementation("androidx.compose.foundation:foundation-layout:${Version.Library.compose}")
    implementation("androidx.compose.animation:animation:${Version.Library.compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")
    implementation("androidx.compose.ui:ui-tooling-preview:${Version.Library.compose}")
    implementation("androidx.datastore:datastore:${Version.Library.dataStore}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Version.Library.compose}")
    // Remove after IDE update
    // Fix to show Preview according to https://issuetracker.google.com/issues/227767363
    debugImplementation("androidx.customview:customview:1.2.0-alpha01")
    debugImplementation("androidx.customview:customview-poolingcontainer:1.0.0-beta01")
}

fun DependencyHandlerScope.protobuf() {
    implementation("com.google.protobuf:protobuf-kotlin:3.19.4")
}

fun DependencyHandlerScope.accompanist() {
    implementation("com.google.accompanist:accompanist-insets:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-insets-ui:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-pager:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-swiperefresh:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-placeholder:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-flowlayout:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-permissions:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-drawablepainter:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-navigation-material:${Version.Library.accompanist}")
    implementation("com.google.accompanist:accompanist-navigation-animation:${Version.Library.accompanist}")
}

fun DependencyHandlerScope.composeConstraintLayout() {
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01")
}

fun DependencyHandlerScope.test() {
    testImplementation("junit:junit:${Version.Test.junit}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.Test.coroutines}")
    testImplementation("org.mockito:mockito-core:${Version.Test.mockito}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Version.Test.mockitoKotlin}")
    testImplementation("io.mockk:mockk:${Version.Test.mockk}")

}

fun DependencyHandlerScope.androidTest() {
    androidTestImplementation("androidx.test:core:${Version.Test.core}")

    androidTestImplementation("androidx.test:runner:${Version.Test.runner}")
    androidTestImplementation("androidx.test:rules:${Version.Test.rules}")

    androidTestImplementation("androidx.test.ext:junit:${Version.Test.junit}")
    androidTestImplementation("androidx.test.ext:truth:${Version.Test.truth}")

    androidTestImplementation("io.mockk:mockk-android:${Version.Test.mockkAndroid}")
}

fun DependencyHandlerScope.implementation(module: Any) = add("implementation", module)

fun DependencyHandlerScope.debugImplementation(module: Any) = add("debugImplementation", module)

fun DependencyHandlerScope.testImplementation(module: Any) = add("testImplementation", module)

fun DependencyHandlerScope.androidTestImplementation(module: Any) = add("androidTestImplementation", module)

fun DependencyHandlerScope.kapt(module: Any) = add("kapt", module)

fun DependencyHandlerScope.project(path: Any) = add("implementation", (project(mapOf("path" to path))))
