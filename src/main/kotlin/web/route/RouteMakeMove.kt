package web.route

import datasource.mapper.GameMapperDatasource
import datasource.repository.RepositoryService
import domain.model.Game
import domain.service.TicTacToeService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.GameMapper
import java.util.*

fun Route.routeMakeMove() {
    val repositoryService: RepositoryService by inject()

    post("game/makeMove/{id}") {
        val gameId = call.parameters["id"]?.let {
            UUID.fromString(it)
        }

        if (gameId == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            return@post
        }


        val existingGame = repositoryService.getGameById(gameId)
            ?.let { it1 -> GameMapperDatasource.toDomain(it1, gameId) }
        val updatedGame: Game
        if (existingGame != null) {
            updatedGame = TicTacToeService.updateGame(existingGame, null)
            repositoryService.updateGame(updatedGame)
            call.respond(GameMapper.fromDomain(updatedGame))
        }
        else {
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

        val existingGame = repositoryService.getGameById(gameId)
            ?.let { it1 -> GameMapperDatasource.toDomain(it1, gameId) }
        val updatedGame: Game
        if (existingGame != null) {
            updatedGame = TicTacToeService.updateGame(existingGame, TicTacToeService.cellToCoordinate(cell))
            repositoryService.updateGame(updatedGame)
            call.respond(GameMapper.fromDomain(updatedGame))
        }
        else {
            call.respond(HttpStatusCode.NotFound, "Игра не найдена")
            return@post
        }


    }
}

