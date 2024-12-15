package datasource.repository

import datasource.model.GameDTO
import domain.model.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RepositoryService (private val repository: GameRepository) {

    fun saveGame(game: Game) {
        repository.saveGame(game)
    }

    fun getGameById(id: UUID): GameDTO? {
        return repository.getGame(id)
    }

    fun removeGameById(id: UUID) {
        return repository.removeGame(id)
    }

    fun updateGame(game: Game) {
        return repository.updateGame(game)
    }

    fun getAllGames(): ConcurrentHashMap<String, GameDTO> {
        val originalMap = repository.getAllGames()
        return originalMap.mapKeys { (key, _) -> key.toString() }.toMap(ConcurrentHashMap())
    }



}