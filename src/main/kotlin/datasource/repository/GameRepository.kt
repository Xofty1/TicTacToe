package datasource.repository

import datasource.model.GameDTO
import domain.model.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameRepository(private val gameStorage: GameStorage) {

    fun saveGame(game: Game) {
        gameStorage.saveGame(game)
    }

    fun getGame(gameId: UUID): GameDTO? {
        return gameStorage.getGame(gameId)
    }

    fun removeGame(gameId: UUID) {
        return gameStorage.removeGame(gameId)
    }

    fun updateGame(game: Game) {
        return gameStorage.updateGame(game)
    }

    fun getAllGames(): ConcurrentHashMap<UUID, GameDTO> {
        return gameStorage.getGames()
    }
}