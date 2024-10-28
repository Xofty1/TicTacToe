import io.ktor.server.engine.*
import io.ktor.server.netty.*
import web.module.configureRouting
import web.module.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
