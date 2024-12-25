package web.model

import datasource.model.GameDTO
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    val login: String,
    val password: String,
    val games: Map<String, GameDTO>
)