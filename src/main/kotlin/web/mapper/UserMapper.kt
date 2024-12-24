package web.mapper

import domain.model.User
import web.model.UserDTO

object UserMapper {
    fun fromDomain(user: User): UserDTO {
        return UserDTO(
            login = user.login,
            password = user.password
        )
    }

    fun toDomain(user: UserDTO): User {
        return User(
            login = user.login,
            password = user.password
        )
    }
}