package web.route

import datasource.repository.TicTacToeService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.GameMapper
import web.model.GameDTO
import java.util.*

fun Route.routeGame() {
    val service: TicTacToeService by inject()

    post("/game/{id}") {

        val gameId = call.parameters["id"]?.let {
            try {
                UUID.fromString(it)
            } catch (e: IllegalArgumentException) {
                null
            }
        }

        if (gameId == null) {
            call.respond(HttpStatusCode.BadRequest, "Некорректный ID игры")
            return@post
        }

        val request = call.receive<GameDTO>()
        val existingGame = service.repository.getGameById(gameId)

        if (existingGame == null) {
            call.respond(HttpStatusCode.NotFound, "Игра не найдена")
            return@post
        }

        val updatedGame = service.updateGame(GameMapper.toDomain(gameId = gameId, gameDTO = request), null)

        service.repository.updateGame(updatedGame)

        call.respond(GameMapper.fromDomain(updatedGame))
    }
}


fun Route.routeAllGames() {
    val service: TicTacToeService by inject()
    get("/games") {
        val games = service.repository.getAllGames()
        call.respond(games)
    }
}
