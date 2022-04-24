import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.protoc
import com.harper.buildsrc.*
import org.gradle.kotlin.dsl.protobuf

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.protobuf")
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

    sourceSets.getByName("main")
        .java.srcDirs("${protobuf.protobuf.generatedFilesBaseDir}/main/java")

    protobuf {
        protobuf.run {
            generatedFilesBaseDir = "$projectDir/src"
            protoc {
                artifact = "com.google.protobuf:protoc:3.15.8"
            }
            generateProtoTasks {
                all().forEach {
                    it.builtins {
                        id("java") {
                            java { }
                        }
                    }
                }
            }
        }
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
    protobuf()

    core()
    database()
    network()
    repository()
    spec()
    shelter()
}