package web.model

import kotlinx.serialization.Serializable

@Serializable
data class GameDTO(
    val board: Array<IntArray>,
    var turn: String,
    var status: String,
    var firstUserLogin: String,
    var secondUserLogin: String? = null,
)