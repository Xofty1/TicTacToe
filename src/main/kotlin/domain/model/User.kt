package domain.model

import java.util.concurrent.CopyOnWriteArrayList

data class User (
    val login: String,
    val password: String,
    val games: CopyOnWriteArrayList<Game>
)