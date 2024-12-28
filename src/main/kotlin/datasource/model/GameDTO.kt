package datasource.model

import domain.utils.STATUS
import domain.utils.TURN
import kotlinx.serialization.Serializable
import web.module.UUIDSerializer
import java.util.*

@Serializable
data class GameDTO(
    val board: Array<IntArray>,
    var turn: TURN,
    var status: STATUS,
    var firstUserLogin: String,
    var secondUserLogin: String? = null,
)