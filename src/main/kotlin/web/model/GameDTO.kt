package web.model

import kotlinx.serialization.Serializable
import web.module.UUIDSerializer
import java.util.*

@Serializable
data class GameDTO(
    val board: Array<IntArray>,
    var turn: String
)