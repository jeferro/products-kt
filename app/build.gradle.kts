import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    id("org.jetbrains.kotlin.kapt")

    id("jacoco")
}

jacoco {
    toolVersion = Versions.jacoco
}

dependencies {
    implementation(project(":lib-infrastructure"))

    // Mockito
    testImplementation("org.mockito.kotlin", "mockito-kotlin", Versions.mockitoKotlin)

    // Coroutines
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", Versions.kotlinxCoroutines)
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor")

    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", Versions.kotlinxCoroutines)

    // Mapstruct
    kapt("org.mapstruct", "mapstruct-processor", Versions.mapstruct)
    implementation("org.mapstruct", "mapstruct", Versions.mapstruct)

    // Spring
    testImplementation("org.springframework.boot", "spring-boot-starter-test")

    // Web
    implementation("org.springframework.boot", "spring-boot-starter-web")

    // Mongo
    implementation("org.springframework.boot", "spring-boot-starter-data-mongodb-reactive")

    testImplementation("org.springframework.boot", "spring-boot-testcontainers")
    testImplementation("org.testcontainers", "junit-jupiter")
    testImplementation("org.testcontainers", "mongodb", Versions.testcontainers)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = Versions.jdk.majorVersion
    }
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(
            files(classDirectories.files.map {
                fileTree(it).apply {
                    exclude(
                        "**/*Application*",
                        "**/*Configuration*",
                        "**/dtos/**",
                        "**/daos/**",
                        "**/params/**",
                        "**/mappers/**"
                    )
                }
            })
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
