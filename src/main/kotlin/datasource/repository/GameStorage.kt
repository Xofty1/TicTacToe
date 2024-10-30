package datasource.repository

import domain.model.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameStorage {
    private val games = ConcurrentHashMap<UUID, Game>()

    fun saveGame(game: Game) {
        games[game.id] = game
    }

    fun getGame(gameId: UUID): Game? {
        return games[gameId]
    }

    fun removeGame(gameId: UUID) {
        games.remove(gameId)
    }

    fun updateGame(game: Game) {
        // Проверяем, существует ли игра с данным ID
        if (games.containsKey(game.id)) {
            games[game.id] = game
        } else {
            throw IllegalArgumentException("Game with ID ${game.id} does not exist.")
        }
    }
}