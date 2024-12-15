package domain.model

import domain.utils.TURN
import kotlinx.serialization.Serializable

@Serializable
data class GameBoard(
    var board: Array<IntArray> = Array(3) { IntArray(3) { 0 } }
) {
    fun isFull(): Boolean {
        return board.all { row -> row.all { it != TURN.NONE.type } }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameBoard

        return board.contentDeepEquals(other.board)
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }
}