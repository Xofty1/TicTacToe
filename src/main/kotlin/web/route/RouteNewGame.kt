package web.route

import datasource.repository.GameStorage
import domain.service.TicTacToeService.createNewGame
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import web.mapper.GameMapper
import java.util.*

fun Route.createGameRoute() {
    post("/game/new") {
        val newGame = createNewGame()
        println(newGame.id) // Дополнить функционалом добавления в хранилище
        call.respond(HttpStatusCode.Created, GameMapper.fromDomain(game = newGame))
    }
}

fun Route.getGameRoute() {
    get("/game/{id}") {
        val gameId = call.parameters["id"]?.let { UUID.fromString(it) }
        if (gameId == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            return@get
        }

        val game = GameStorage.getGame(gameId)
        if (game == null) {
            call.respond(HttpStatusCode.NotFound, "Game not found")
        } else {
            call.respond(HttpStatusCode.OK, game)
        }
    }
}