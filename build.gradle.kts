import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") apply false
}

group = "ru.otus.otuskotlin.cryptomarket"
version = "0.0.1"

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "18"
    }
}