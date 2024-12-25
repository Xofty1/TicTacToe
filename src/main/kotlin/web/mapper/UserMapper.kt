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
            password = user.password,
            games = user.games.associate { it.id.toString() to GameMapperDatasource.fromDomain(game = it) },
        )
    }

    fun toDomain(userDTO: UserDTO): User {
        return User(
            login = userDTO.login,
            password = userDTO.password,
            games = CopyOnWriteArrayList(
                userDTO.games.map { (id, gameDTO) ->
                    GameMapperDatasource.toDomain(gameDTO, UUID.fromString(id))
                }
            )

        )
    }
}