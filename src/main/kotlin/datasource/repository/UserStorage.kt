package datasource.repository

import datasource.model.GameDTO
import datasource.model.UserDTO
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.log

class UserStorage {
    private val users = ConcurrentHashMap<String, UserDTO>()

    fun getUsers(): ConcurrentHashMap<String, UserDTO> {
        return users
    }

    fun saveUser(user: UserDTO, login: String) {
        if (users[login] == null) {
            users[login] = user
        } else {
            throw IllegalArgumentException("User with login $login exists.")
        }
    }

    fun getUser(login: String): UserDTO? {
        return users[login]
    }

    fun removeGame(login: String) {
        users.remove(login)
    }

}