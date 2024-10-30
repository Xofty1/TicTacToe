package datasource.repository

import domain.model.Game
import java.util.*

class GameRepository(private val gameStorage: GameStorage) {

    fun saveGame(game: Game) {
        gameStorage.saveGame(game)
    }

    fun getGame(gameId: UUID): Game? {
        return gameStorage.getGame(gameId)
    }

    fun removeGame(gameId: UUID) {
        return gameStorage.removeGame(gameId)
    }

    fun updateGame(game: Game) {
        return gameStorage.updateGame(game)
    }


}