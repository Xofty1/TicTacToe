import datasource.repository.GameRepository
import datasource.repository.GameStorage
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import web.module.configureCORS
import web.module.configureKoin
import web.module.configureRouting
import web.module.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureKoin()
        configureCORS()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
