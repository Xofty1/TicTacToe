package datasource.model

import kotlinx.serialization.Serializable
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Serializable
data class UserDTO (
    val password: String,
)