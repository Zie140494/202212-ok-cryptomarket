plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    val kotestVersion: String by project
    val springdocOpenapiUiVersion: String by project
    val springmockkVersion: String by project
    val stdLibVersion: String by project
    val coroutinesVersion: String by project


    implementation("org.springframework.boot:spring-boot-starter-actuator") // info; refresh; springMvc output
    implementation("org.springframework.boot:spring-boot-starter-web") // Controller, Service, etc..
    // implementation("org.springframework.boot:spring-boot-starter-websocket") // Controller, Service, etc..
    implementation("org.springdoc:springdoc-openapi-ui:$springdocOpenapiUiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // from models to json and Vice versa
    implementation("org.jetbrains.kotlin:kotlin-reflect") // for spring-boot app
    implementation("org.jetbrains.kotlin:kotlin-stdlib") // for spring-boot app
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${coroutinesVersion}")

    //implementation("org.jetbrains.kotlin:kotlin-main-kts:1.6.0")

    // transport models
    implementation(project(":ok-cryptomarket-common"))
    implementation(project(":ok-cryptomarket-lib-logging-logback"))

    // api
    implementation(project(":ok-cryptomarket-api-jackson"))
    implementation(project(":ok-cryptomarket-mappers"))

    // Biz
    implementation(project(":ok-cryptomarket-biz"))

    // Logging
    implementation(project(":ok-cryptomarket-mappers-log1"))
    implementation(project(":ok-cryptomarket-api-log1"))

    // Repository
    implementation(project(":ok-cryptomarket-repo-in-memory"))

    // Repo
    implementation(project(":ok-cryptomarket-repo-stubs"))
    implementation(project(":ok-cryptomarket-repo-in-memory"))
    implementation(project(":ok-cryptomarket-repo-postgresql"))

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(project(":ok-cryptomarket-stubs"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:$springmockkVersion") // mockking beans
}

tasks {
    withType<ProcessResources> {
        from("$rootDir/specs") {
            into("/static")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
