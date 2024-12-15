package datasource.repository

import datasource.mapper.GameMapperDatasource
import datasource.model.GameDTO
import domain.model.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RepositoryService (private val repository: GameRepository) {

    fun saveGame(game: Game) {
        repository.saveGame(GameMapperDatasource.fromDomain(game), game.id)
    }

    fun getGameById(id: UUID): GameDTO? {
        return repository.getGame(id)
    }

    fun removeGameById(id: UUID) {
        return repository.removeGame(id)
    }

    fun updateGame(game: Game) {
        repository.updateGame(GameMapperDatasource.fromDomain(game), game.id)
    }

    fun getAllGames(): ConcurrentHashMap<String, GameDTO> {
        val originalMap = repository.getAllGames()
        return originalMap.mapKeys { (key, _) -> key.toString() }.toMap(ConcurrentHashMap())
    }



}