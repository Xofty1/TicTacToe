package domain.service

import com.sun.org.apache.xpath.internal.operations.Bool
import domain.model.Game
import domain.model.GameBoard
import domain.utils.TURN

interface GameService {
    fun getNextMove(game: Game, turn: TURN): Pair<Int, Int>
    fun validateBoard(game: Game): Boolean
    fun checkWin(board: Array<IntArray>, player: Int): Boolean
}