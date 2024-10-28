package web.model

import domain.utils.TURN
import kotlinx.serialization.Serializable
import web.module.UUIDSerializer
import java.util.*

@Serializable
data class GameDTO(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val board: Array<IntArray>,
    var turn: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameDTO

        if (id != other.id) return false
        if (!board.contentDeepEquals(other.board)) return false
        if (turn != other.turn) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + board.contentDeepHashCode()
        result = 31 * result + turn.hashCode()
        return result
    }
}