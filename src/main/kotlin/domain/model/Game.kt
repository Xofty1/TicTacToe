package domain.model

import domain.utils.TURN
import java.util.UUID

data class Game (
    val id: UUID,
    val board: GameBoard,
    var turn: TURN
)