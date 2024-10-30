package domain.service

import domain.model.Game
import domain.utils.TURN

interface GameService {
    fun getNextMove(game: Game): Pair<Int, Int>
    fun validateBoard(game: Game): Boolean
    fun checkWin(board: Array<IntArray>, player: Int): Boolean
}