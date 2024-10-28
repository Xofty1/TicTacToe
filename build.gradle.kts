plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
    id("io.ktor.plugin") version "2.3.8"
    application
}



group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Ktor Core
    implementation("io.ktor:ktor-server-core:2.0.0")
    implementation("io.ktor:ktor-server-netty:2.0.0") // для запуска на Netty

    // Content negotiation and JSON serialization
    implementation("io.ktor:ktor-server-content-negotiation:2.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    // Call logging
    implementation("io.ktor:ktor-server-call-logging:2.0.0")

    // Koin for Dependency Injection
    implementation("io.insert-koin:koin-ktor:3.1.4")
    implementation("io.insert-koin:koin-core:3.1.4")

    // Logback for logging
    implementation("ch.qos.logback:logback-classic:1.2.6")

    // Testing
    testImplementation("io.ktor:ktor-server-tests:2.0.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
}
tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}