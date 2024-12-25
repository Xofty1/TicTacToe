package datasource.mapper

import datasource.model.UserDTO
import domain.model.User
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

object UserMapperDatasource {
    fun fromDomain(user: User): UserDTO {
        return UserDTO(
            password = user.password,
            games = user.games.associate { it.id.toString() to GameMapperDatasource.fromDomain(game = it) },
        )
    }


    fun toDomain(userDTO: UserDTO, login: String): User {
        return User(
            login = login,
            password = userDTO.password,
            games = CopyOnWriteArrayList(
                userDTO.games.map { (id, gameDto) ->
                    GameMapperDatasource.toDomain(gameDto, UUID.fromString(id))
                }
            )
        )
    }

}
