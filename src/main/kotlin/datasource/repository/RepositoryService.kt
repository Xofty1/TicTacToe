package datasource.repository

import domain.model.Game
import java.util.*

class RepositoryService (private val repository: GameRepository) {

    fun saveGame(game: Game) {
        repository.saveGame(game)
    }

    fun getGameById(id: UUID): Game? {
        return repository.getGame(id)
    }

    fun removeGameById(id: UUID) {
        return repository.removeGame(id)
    }

    fun updateGame(game: Game) {
        return repository.updateGame(game)
    }

}