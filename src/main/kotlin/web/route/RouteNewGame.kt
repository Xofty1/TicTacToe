package web.route

import datasource.repository.TicTacToeService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.GameMapper
import java.util.*

fun Route.createGameRoute() {
    val service: TicTacToeService by inject()

    post("/game/new") {
        val newGame = service.createNewGame()
        service.repository.saveGame(newGame)
        call.respond(HttpStatusCode.Created, newGame.id.toString())
    }
}

fun Route.getGameRoute() {
    val service: TicTacToeService by inject()
    get("/game/{id}") {
        val gameId = call.parameters["id"]?.let { UUID.fromString(it) }
        if (gameId == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            return@get
        }

        val game = service.repository.getGameById(gameId)
        if (game == null) {
            call.respond(HttpStatusCode.NotFound, "Game not found")
        } else {
            call.respond(HttpStatusCode.OK, GameMapper.fromDomain(game))
        }
    }
}