package datasource.repository

import datasource.mapper.GameMapperDatasource
import datasource.model.GameDTO
import domain.model.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameRepository(private val gameStorage: GameStorage) {

    fun saveGame(game: Game) {
        gameStorage.saveGame(GameMapperDatasource.fromDomain(game), game.id)
    }

    fun removeGame(gameId: UUID) {
        return gameStorage.removeGame(gameId)
    }

    fun updateGame(game: Game) {
        gameStorage.updateGame(GameMapperDatasource.fromDomain(game), game.id)
    }

    fun getAllGames(): ConcurrentHashMap<String, GameDTO> {
        val originalMap = gameStorage.getGames()
        return originalMap.mapKeys { (key, _) -> key.toString() }.toMap(ConcurrentHashMap())
    }

    fun getGameById(id: UUID): Game? {
        return gameStorage.getGame(id)?.let { GameMapperDatasource.toDomain(it, id) }
    }

}