package datasource.repository

import domain.model.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameStorage {
    private val games = ConcurrentHashMap<UUID, Game>()

    // Метод для сохранения игры
    fun saveGame(game: Game) {
        games[game.id] = game
    }

    // Метод для получения игры по UUID
    fun getGame(gameId: UUID): Game? {
        return games[gameId]
    }

    // Метод для удаления игры, если это нужно
    fun removeGame(gameId: UUID) {
        games.remove(gameId)
    }
}