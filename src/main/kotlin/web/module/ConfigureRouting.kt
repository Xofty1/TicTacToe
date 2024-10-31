package web.module

import di.gameModule
import io.ktor.server.application.*
import io.ktor.server.routing.*
import web.route.createGameRoute
import web.route.getGameRoute
import web.route.routeGame
import org.koin.ktor.plugin.Koin
import web.route.routeMakeMove

fun Application.configureKoin() {
    install(Koin) {
        modules(gameModule)
    }
}

fun Application.configureRouting() {
    routing {
        routeGame()
        getGameRoute()
        createGameRoute()
        routeMakeMove()
    }
}
