package web.route

import datasource.mapper.GameMapperDatasource
import datasource.repository.RepositoryService
import domain.service.TicTacToeService.createNewGame
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.GameMapper
import java.util.*

fun Route.createGameRoute() {
    val repositoryService: RepositoryService by inject()

    post("/game/new") {
        val newGame = createNewGame()
        repositoryService.saveGame(newGame)
        call.respond(HttpStatusCode.Created, newGame.id.toString())
    }
}

fun Route.getGameRoute() {
    val repositoryService: RepositoryService by inject()
    get("/game/{id}") {
        val gameId = call.parameters["id"]?.let { UUID.fromString(it) }
        if (gameId == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            return@get
        }

        val game = repositoryService.getGameById(gameId)
        val domainGame = game?.let { it1 -> GameMapperDatasource.toDomain(it1, gameId) }
        if (domainGame == null) {
            call.respond(HttpStatusCode.NotFound, "Game not found")
        } else {
            call.respond(HttpStatusCode.OK, GameMapper.fromDomain(domainGame))
        }
    }
}