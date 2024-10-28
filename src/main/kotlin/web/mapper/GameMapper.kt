package web.mapper

import domain.model.Game
import domain.model.GameBoard
import domain.utils.TURN
import web.model.GameDTO

object GameMapper {
    fun fromDomain(game: Game): GameDTO {
        var turn = "O"
        if (game.turn == TURN.X){
            turn = "X"
        }
        return GameDTO(
            id = game.id,
            board = game.board.board,
            turn = turn
        )
    }

    fun toDomain(gameDTO: GameDTO): Game {
        var turn = TURN.O
        if (gameDTO.turn == "X"){
            turn = TURN.X
        }
        return Game(
            id = gameDTO.id,
            board = GameBoard(gameDTO.board),
            turn = turn
        )
    }
}