package web.module

import io.ktor.server.application.*

fun Application.configureApplication() {
    configureKoin()
    configureCORS()
    configureStatic()
    configureSerialization()
    configureRouting()
}
