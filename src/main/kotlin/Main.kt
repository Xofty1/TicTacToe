import io.ktor.server.engine.*
import io.ktor.server.netty.*
import web.module.*


fun main() {
    embeddedServer(Netty, port = 8080) {
        configureKoin()
        configureCORS()
        configureStatic()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
