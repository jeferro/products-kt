import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")

    id("org.openapi.generator")
}

val base_package = "${group}.components.products.rest"

dependencies {
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-reactive", Versions.kotlinxCoroutines)

    api("org.springframework.boot", "spring-boot-starter-web")
    api("jakarta.validation", "jakarta.validation-api")

    implementation("org.springdoc", "springdoc-openapi-data-rest", Versions.openapi)
    implementation("org.springdoc", "springdoc-openapi-ui", Versions.openapi)
    implementation("org.springdoc", "springdoc-openapi-kotlin", Versions.openapi)

}


openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/openapi.yml")

    packageName.set("${base_package}")
    apiPackage.set("${base_package}.apis")
    modelPackage.set("${base_package}.dtos")
    modelNameSuffix.set("RestDTO")
    apiNameSuffix.set("Api")

    generateApiTests.set(false)
    configOptions.set(mapOf(
            "useTags" to "true",
            "reactive" to "true",
            "gradleBuildFile" to "false",
            "useSpringBoot3" to "true",
            "documentationProvider" to "none",
            "interfaceOnly" to "true"
    ))
}

tasks.withType<Test> {
    finalizedBy(tasks.openApiGenerate)
}

java.sourceSets["main"].java {
    srcDir("${projectDir}/build/generate-resources")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
