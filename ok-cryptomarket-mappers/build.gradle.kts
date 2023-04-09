plugins {
    kotlin("jvm")
}

group = "ru.otus.otuskotlin.cryptomarket"
version = "0.0.1"



dependencies {

    implementation(project(":ok-cryptomarket-api-jackson"))
    implementation(project(":ok-cryptomarket-common"))
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test-junit"))
}