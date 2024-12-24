package datasource.repository

import datasource.mapper.UserMapperDatasource
import domain.model.User

class UserRepository (private val userStorage: UserStorage) {
    fun saveUser(user: User) {
        userStorage.saveUser(UserMapperDatasource.fromDomain(user), user.login)
    }

    fun getUserByLogin(login: String, password: String): User? {
        val findUser = userStorage.getUser(login)
        var user: User? = null
        if (findUser != null) {
            if (password == findUser.password){
                user =  UserMapperDatasource.toDomain(findUser, login)
            }
        }
        return user
    }
}