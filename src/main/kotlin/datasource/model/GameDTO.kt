package datasource.model

import domain.utils.STATUS
import domain.utils.TURN
import java.util.*

data class GameDTO(
    val id: UUID,
    val board: Array<IntArray>,
    var turn: TURN,
    var status: STATUS
)