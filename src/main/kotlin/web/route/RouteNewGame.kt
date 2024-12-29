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
import web.model.UserDTO
import java.util.*

fun Route.createGameRoute() {
    val service: TicTacToeService by inject()

    post("/game/new") {
        // Извлечение заголовка Authorization
        val request = try {
            call.receive<GameDTO>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            return@post
        }
        val credentials = call.getCredentialsOrRespondUnauthorized() ?: return@post
        val (login, password) = credentials

        val user = service.userRepository.getUserByLoginAndPassword(login, password)
        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid login or password")
            return@post
        }
        val requestGame = request
        // Создаем новую игру
        val newGame = service.createNewGame(user.login, requestGame.secondUserLogin)

        // Сохраняем игру в глобальном репозитории
        service.repository.saveGame(newGame)

        service.userRepository.updateUser(user)

        // Отправляем ответ с идентификатором новой игры
        call.respond(HttpStatusCode.Created, mapOf(newGame.id.toString() to GameMapper.fromDomain(newGame)))
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