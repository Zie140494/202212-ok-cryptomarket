plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common:1.6.0"))

                implementation(project(":ok-cryptomarket-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib:1.6.0"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit:1.6.0"))
            }
        }
    }
}
