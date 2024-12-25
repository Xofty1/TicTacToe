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
        // Извлечение заголовка Authorization
        val authHeader = call.request.headers["Authorization"]
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Basic ")) {
            call.respond(HttpStatusCode.Unauthorized, "Missing or invalid Authorization header")
            return@post
        }

        // Декодируем Basic Auth
        val credentials = decodeBasicAuth(authHeader)
        if (credentials == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials format")
            return@post
        }

        val (login, password) = credentials

        // Поиск пользователя
        val user = service.userRepository.getUserByLoginAndPassword(login, password)
        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid login or password")
            return@post
        }

        // Создаем новую игру
        val newGame = service.createNewGame()

        // Сохраняем игру в глобальном репозитории
        service.repository.saveGame(newGame)

        // Добавляем игру пользователю
        user.games.add(newGame)
        service.userRepository.updateUser(user) // Сохраняем изменения в репозитории

        // Отправляем ответ с идентификатором новой игры
        call.respond(HttpStatusCode.Created, newGame.id.toString())
    }
}

private fun decodeBasicAuth(authHeader: String): Pair<String, String>? {
    return try {
        val base64Credentials = authHeader.removePrefix("Basic ").trim()
        val decodedCredentials = String(Base64.getDecoder().decode(base64Credentials))
        val split = decodedCredentials.split(":", limit = 2)
        if (split.size == 2) split[0] to split[1] else null
    } catch (e: IllegalArgumentException) {
        null // Невалидный Base64
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