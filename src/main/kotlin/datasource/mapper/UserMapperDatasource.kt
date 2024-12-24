package datasource.mapper

import datasource.model.UserDTO
import domain.model.User

object UserMapperDatasource {
    fun fromDomain(user: User): UserDTO {
        return UserDTO(
           password = user.password,
        )
    }


    fun toDomain(userDTO: UserDTO, login: String): User {
        return User(
            login = login,
            password = userDTO.password
        )
    }
}
