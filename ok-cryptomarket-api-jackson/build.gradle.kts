plugins {
    kotlin("jvm")
    id("org.openapi.generator")
}

dependencies {
    val jacksonVersion: String by project
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("org.testng:testng:7.7.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.0")
}

sourceSets {
    main {
        java.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
    }
}

/**
 * Настраиваем генерацию здесь
 */
openApiGenerate {
    val openapiGroup = "${rootProject.group}.api"
    generatorName.set("kotlin") // Это и есть активный генератор
    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set("$rootDir/specs/specs-or.yaml")

    /**
     * Здесь указываем, что нам нужны только модели, все остальное не нужно
     * https://openapi-generator.tech/docs/globals
     */
    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    /**
     * Настройка дополнительных параметров из документации по генератору
     * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
     */
    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )
}

tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}
