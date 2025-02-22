/*
 * Jopiter APP
 * Copyright (C) 2021 Leonardo Colman Lopes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


group = "app.jopiter"

plugins {
    application
    kotlin("jvm") version "1.5.20"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.20" apply false

    kotlin("plugin.spring") version "1.5.20"
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    id("io.gitlab.arturbosch.detekt") version "1.16.0"
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "kotlin-spring")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven(url = "https://jitpack.io")
    }

    dependencies {
        // Kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.20")

        // Spring
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springdoc:springdoc-openapi-ui:1.5.9")
        implementation("org.springdoc:springdoc-openapi-kotlin:1.5.9")
        implementation("org.springdoc:springdoc-openapi-webmvc-core:1.5.9")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.0.0")


        // Fuel
        implementation("com.github.kittinunf.fuel:fuel:2.3.1")
        implementation("com.github.kittinunf.fuel:fuel-jackson:2.3.1")
        implementation("com.github.kittinunf.fuel:fuel-coroutines:2.3.1")

        // Kotest
        testImplementation("io.kotest:kotest-runner-junit5:4.6.1")
        testImplementation("io.kotest:kotest-assertions-json:4.6.1")
        testImplementation("io.kotest:kotest-property:4.6.1")

        // Mockk
        testImplementation("io.mockk:mockk:1.11.0")

        // Mock Server
        testImplementation("org.mock-server:mockserver-netty:5.11.2") {
            exclude("com.google.guava")
        }

        // Root project
        testImplementation(rootProject)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            exceptionFormat = FULL
            showCauses = true
            showStandardStreams = true
            events(FAILED, PASSED)
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

dependencies {
    implementation(project(":privacy"))
    implementation(project(":restaurants"))
    implementation(project(":timetable"))
}

