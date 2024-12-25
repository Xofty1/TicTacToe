package datasource.repository

import at.favre.lib.crypto.bcrypt.BCrypt
import datasource.mapper.UserMapperDatasource
import domain.model.User
import web.mapper.UserMapper

class UserRepository(private val userStorage: UserStorage) {
    fun saveUser(user: User) {
        userStorage.saveUser(UserMapperDatasource.fromDomain(user), user.login)
    }

    fun getUsers(): List<User> {
        return userStorage.getUsers().map { (login, userDTO) ->
            UserMapperDatasource.toDomain(userDTO, login)
        }
    }

    fun updateUser(user: User) {
        userStorage.updateUser(UserMapperDatasource.fromDomain(user), user.login)
    }

    fun getUserByLoginAndPassword(login: String, password: String): User? {
        val findUser = userStorage.getUser(login)
        return if (findUser != null && password == findUser.password) {
            UserMapperDatasource.toDomain(findUser, login)
        } else {
            null
        }
    }

    fun getUserByLogin(login: String): Boolean {
        val findUser = userStorage.getUser(login)
        return if (findUser != null) {
            true
        } else {
            false
        }
    }



    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

}