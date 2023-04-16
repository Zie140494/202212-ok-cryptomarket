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

    implementation("org.springframework.boot:spring-boot-starter-actuator") // info; refresh; springMvc output
    implementation("org.springframework.boot:spring-boot-starter-web") // Controller, Service, etc..
    // implementation("org.springframework.boot:spring-boot-starter-websocket") // Controller, Service, etc..
    implementation("org.springdoc:springdoc-openapi-ui:$springdocOpenapiUiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // from models to json and Vice versa
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0") // for spring-boot app
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0") // for spring-boot app

    // transport models
    implementation(project(":ok-cryptomarket-common"))

    // api
    implementation(project(":ok-cryptomarket-api-jackson"))
    implementation(project(":ok-cryptomarket-mappers"))

    // Stubs
    implementation(project(":ok-cryptomarket-stubs"))

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:4.0.2") // mockking beans
}

tasks {
    withType<ProcessResources> {
        from("$rootDir/specs") {
            into("/static")
        }
    }
}

/*sourceSets {
    main {
        resources {
            srcDirs("$rootDir/specs")
            println(srcDirs)
        }
    }
}*/

tasks.withType<Test> {
    useJUnitPlatform()
}