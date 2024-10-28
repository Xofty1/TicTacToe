package web.route

import datasource.repository.GameStorage
import web.model.GameDTO
import domain.service.TicTacToeService.updateGame

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import web.mapper.GameMapper
import java.util.*

fun Route.routeGame() {
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
        if (request.id != gameId) {
            call.respond(HttpStatusCode.BadRequest, "ID игры в параметре и теле не совпадают")
            return@post
        }

        val updatedGame = updateGame(GameMapper.toDomain(gameDTO = request))
        // Нет корректного функционала с добавлением в хранилище
        GameStorage.removeGame(updatedGame.id)
        GameStorage.saveGame(updatedGame)
        call.respond(GameMapper.fromDomain(updatedGame))
        println("Игра обновлена")
    }
}

