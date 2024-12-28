package domain.model

import domain.utils.STATUS
import domain.utils.TURN
import kotlinx.serialization.Serializable
import web.module.UUIDSerializer
import java.util.UUID

@Serializable
data class Game(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val board: GameBoard,
    var turn: TURN,
    var status: STATUS = STATUS.NONE,
    var firstUserLogin: String,
    var secondUserLogin: String? = null,
)