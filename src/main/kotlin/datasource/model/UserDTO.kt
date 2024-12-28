package datasource.model

import kotlinx.serialization.Serializable
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

@Serializable
data class UserDTO (
    val password: String
)