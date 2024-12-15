package datasource.repository

import datasource.mapper.GameMapperDatasource
import datasource.model.GameDTO
import domain.model.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameStorage {
    private val games = ConcurrentHashMap<UUID, GameDTO>()

    fun getGames(): ConcurrentHashMap<UUID, GameDTO>{
        return games
    }
    fun saveGame(game: GameDTO, id: UUID) {
        games[id] =  game
    }

    fun getGame(gameId: UUID): GameDTO? {
        return games[gameId]
    }

    fun removeGame(gameId: UUID) {
        games.remove(gameId)
    }

    fun updateGame(game: GameDTO, id: UUID) {
        // Проверяем, существует ли игра с данным ID
        if (games.containsKey(id)) {
            games[id] = game
        } else {
            throw IllegalArgumentException("Game with ID ${id} does not exist.")
        }
    }
}