package web.mapper

import datasource.mapper.GameMapperDatasource
import domain.model.User
import web.model.UserDTO
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

object UserMapper {
    fun fromDomain(user: User): UserDTO {
        return UserDTO(
            login = user.login,
            password = user.password
        )
    }

    fun toDomain(userDTO: UserDTO): User {
        return User(
            login = userDTO.login,
            password = userDTO.password,
        )
    }
}