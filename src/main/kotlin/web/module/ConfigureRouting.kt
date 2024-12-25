package web.module

import di.gameModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.routing
import web.route.*

fun Application.configureCORS() {
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        allowCredentials = true
    }
}

fun Application.configureKoin() {
    install(Koin) {
        modules(gameModule)
    }
}

fun Application.configureRouting() {
    routing {
        routeGame()
        routeNewUser()
        routeGetUser()
        userLoginRoute()
        getGameRoute()
        createGameRoute()
        routeMakeMove()
        routeGetUsers()
        routeAllGames()

    }
}
