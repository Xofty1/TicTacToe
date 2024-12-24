package web.route

import domain.model.Game
import datasource.repository.TicTacToeService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.GameMapper
import java.util.*

fun Route.routeMakeMove() {
    val service: TicTacToeService by inject()

    post("game/makeMove/{id}") {
        val gameId = call.parameters["id"]?.let {
            UUID.fromString(it)
        }

        if (gameId == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            return@post
        }


        val existingGame = service.repository.getGameById(gameId)
        val updatedGame: Game
        if (existingGame != null) {
            updatedGame = service.updateGame(existingGame, null)
            service.repository.updateGame(updatedGame)
            call.respond(GameMapper.fromDomain(updatedGame))
        } else {
            call.respond(HttpStatusCode.NotFound, "Игра не найдена")
            return@post
        }
    }

    post("game/makeMove/{id}/{cell}") {
        val gameId = call.parameters["id"]?.let {
            UUID.fromString(it)
        }
        val cell = call.parameters["cell"]?.toInt()

        if (gameId == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            return@post
        }

        if (cell == null || cell < 0 || cell > 8) {
            call.respond(HttpStatusCode.BadRequest, "Invalid cell")
            return@post
        }

        val existingGame = service.repository.getGameById(gameId)
        val updatedGame: Game
        if (existingGame != null) {
            updatedGame = service.updateGame(existingGame, service.cellToCoordinate(cell))
            service.repository.updateGame(updatedGame)
            call.respond(GameMapper.fromDomain(updatedGame))
        } else {
            call.respond(HttpStatusCode.NotFound, "Game not found")
            return@post
        }


    }
}

