package web.mapper

import domain.model.Game
import domain.model.GameBoard
import domain.utils.STATUS
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
            turn = turn,
            status = game.status.result
        )
    }

    fun toDomain(gameId: UUID, gameDTO: GameDTO): Game {
        var turn = TURN.O
        if (gameDTO.turn == "X") {
            turn = TURN.X
        }
        var status = STATUS.NONE
        when (gameDTO.status){
            STATUS.O_WIN.result -> status = STATUS.O_WIN
            STATUS.X_WIN.result -> status = STATUS.X_WIN
            STATUS.DRAW.result -> status = STATUS.DRAW
        }
        return Game(
            id = gameId,
            board = GameBoard(gameDTO.board),
            turn = turn,
            status = status
        )
    }
}