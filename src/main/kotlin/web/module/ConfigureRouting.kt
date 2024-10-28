package web.module

import io.ktor.server.application.*
import io.ktor.server.routing.*
import web.route.createGameRoute
import web.route.getGameRoute
import web.route.routeGame

fun Application.configureRouting() {
    routing {
        routeGame()
        getGameRoute()
        createGameRoute()
    }
}
