plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project
        val logbackVersion: String by project
        val logbackEncoderVersion: String by project
        val kermitLoggerVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("co.touchlab:kermit:$kermitLoggerVersion")
                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                // logback
                implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
                api("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
