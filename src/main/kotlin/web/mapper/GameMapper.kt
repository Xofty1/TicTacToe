package web.mapper

import domain.model.Game
import domain.model.GameBoard
import domain.utils.TURN
import web.model.GameDTO
import java.util.*

object GameMapper {
    fun fromDomain(game: Game): GameDTO {
        var turn = "O"
        if (game.turn == TURN.X) {
            turn = "X"
        }
        return GameDTO(
            board = game.board.board,
            turn = turn
        )
    }

    fun toDomain(gameId: UUID, gameDTO: GameDTO): Game {
        var turn = TURN.O
        if (gameDTO.turn == "X") {
            turn = TURN.X
        }
        return Game(
            id = gameId,
            board = GameBoard(gameDTO.board),
            turn = turn
        )
    }
}