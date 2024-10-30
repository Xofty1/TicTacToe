package datasource.mapper


import datasource.model.GameDTO
import domain.model.Game
import domain.model.GameBoard

object GameMapper {
    fun fromDomain(game: Game): GameDTO {
        return GameDTO(
            id = game.id,
            board = game.board.board,
            turn = game.turn
        )
    }

    fun toDomain(gameDTO: GameDTO): Game {
        return Game(
            id = gameDTO.id,
            board = GameBoard(gameDTO.board),
            turn = gameDTO.turn
        )
    }
}
