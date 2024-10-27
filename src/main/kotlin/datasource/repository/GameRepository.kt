package datasource.repository

import domain.model.Game
import java.util.*

class GameRepository(private val gameStorage: GameStorage) {

    // Метод для сохранения текущей игры
    fun saveGame(game: Game) {
        gameStorage.saveGame(game)
    }

    // Метод для получения текущей игры по UUID
    fun getGame(gameId: UUID): Game? {
        return gameStorage.getGame(gameId)
    }
}