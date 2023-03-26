rootProject.name = "ok-cryptomarket-202212"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion
        id("io.kotest.multiplatform") version kotestVersion apply false
    }
}

include("m1l1-quickstart")
include("m2l3-testing")
