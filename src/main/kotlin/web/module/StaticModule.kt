package web.module

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureStatic() {
    routing {
        staticResources("/", "html") {
            staticResources("/", "index.html")
        }

        staticResources("/js", "js")
        
        staticResources("/styles", "styles")
        staticResources("/image", "image")
    }
}