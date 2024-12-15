package datasource.mapper


import datasource.model.GameDTO
import domain.model.Game
import domain.model.GameBoard
import java.util.UUID

object GameMapperDatasource {
    fun fromDomain(game: Game): GameDTO {
        return GameDTO(
            board = game.board.board,
            turn = game.turn,
            status = game.status
        )
    }


    fun toDomain(gameDTO: GameDTO, id: UUID): Game {
        return Game(
            id = id,
            board = GameBoard(gameDTO.board),
            turn = gameDTO.turn,
            status = gameDTO.status
        )
    }
}
