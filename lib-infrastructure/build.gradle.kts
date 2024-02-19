import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"

    kotlin("jvm") version Versions.jvmPlugin
    kotlin("plugin.spring") version "1.9.22"
}

dependencies {
    api(project(":lib-domain"))

    // Mapstruct
    api("org.mapstruct", "mapstruct", Versions.mapstruct)

    // Spring
    api("org.springframework.boot", "spring-boot-starter")

    // Web
    api("org.springframework.boot", "spring-boot-starter-web")

    // Spring Security (JWT)
    api("org.springframework.boot", "spring-boot-starter-security")
    implementation("com.auth0", "java-jwt", Versions.jwt)
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}