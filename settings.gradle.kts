rootProject.name = "ok-cryptomarket-202212"

pluginManagement {
    val kotlinVersion: String by settings
    val kotestVersion: String by settings
    val openapiVersion: String by settings
    val springframeworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val pluginSpringVersion: String by settings
    val pluginJpa: String by settings
    val bmuschkoVersion: String by settings

    plugins {

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

//include("m1l1-quickstart")
//include("m2l3-testing")
include("ok-cryptomarket-api-jackson")

include("ok-cryptomarket-common")
include("ok-cryptomarket-mappers")

include("ok-cryptomarket-stubs")
include("ok-cryptomarket-app-spring")


include("ok-cryptomarket-biz")
include("ok-cryptomarket-app-kafka")

include("ok-cryptomarket-lib-cor")
include("ok-cryptomarket-lib-logging-common")

include("ok-cryptomarket-mappers-log1")

include("ok-cryptomarket-api-log1")
