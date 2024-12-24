package web.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    val login: String,
    val password: String
)