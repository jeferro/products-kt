import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.jvmPlugin

    kotlin("plugin.spring") version "1.9.22" apply false
    id("org.springframework.boot") version "3.2.2" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false

    id("org.jetbrains.kotlin.kapt") version "1.9.21" apply false

    id("org.openapi.generator") version "7.1.0" apply false
}


subprojects {
    group = "com.jeferro.products"
    version = "0.7.0-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")

    java {
        sourceCompatibility = Versions.jdk
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = Versions.jdk.majorVersion
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

