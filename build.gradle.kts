import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

group = "ru.otus.otuskotlin.marketplace"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

