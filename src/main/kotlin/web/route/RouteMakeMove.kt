package web.route

import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.*

fun Route.routeMakeMove() {
    post("game/makeMove/{id}") {
        val gameId = call.parameters["id"]?.let {
            UUID.fromString(it)
        }
    }
}