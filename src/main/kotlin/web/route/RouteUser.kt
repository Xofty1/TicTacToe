package web.route

import at.favre.lib.crypto.bcrypt.BCrypt
import datasource.repository.TicTacToeService
import domain.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.UserMapper
import web.model.UserDTO
import java.security.MessageDigest
import java.util.*

fun Route.routeNewUser() {
    val service: TicTacToeService by inject()

    post("/user/new") {
        // Получаем тело запроса
        val request = try {
            call.receive<UserDTO>() // Класс запроса
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            return@post
        }

        // Валидация данных
        val (login, password) = request
        if (login.isBlank() || password.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Login and password cannot be empty")
            return@post
        }

//        if (password.length < 8) {
//            call.respond(HttpStatusCode.BadRequest, "Password must be at least 8 characters")
//            return@post
//        }

        // Проверяем, существует ли пользователь
        val existingUser = service.userRepository.getUserByLoginAndPassword(login, password)
        if (existingUser != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
            return@post
        }

        // Создаем пользователя
//        val hashedPassword = hashPasswordBase64(password) // Хеширование пароля
        val newUser = User(login, password)
        service.userRepository.saveUser(newUser)

        // Возвращаем успешный ответ
        call.respond(HttpStatusCode.Created, UserMapper.fromDomain(newUser))
    }
}


fun hashPasswordBase64(password: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
    return Base64.getEncoder().encodeToString(bytes)
}


fun verifyPassword(password: String, hashedPassword: String): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
}


fun Route.routeGetUsers() {
    val service: TicTacToeService by inject()

    get("/users") {
        call.respond(service.userRepository.getUsers().map { it ->
            UserMapper.fromDomain(it)
        })
        return@get
    }
}


fun Route.routeGetUser() {
    val service: TicTacToeService by inject()

    post("/user/exist") {
        val request = try {
            call.receive<String>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request format")
            return@post
        }

        val login = request
        if (login.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Login cannot be empty")
            return@post
        }

        val user = service.userRepository.getUserByLogin(login)
        if (user) {
            call.respond(HttpStatusCode.OK, true)
            return@post
        } else {
            call.respond(HttpStatusCode.NotFound, false)
            return@post
        }
    }
}

fun Route.userLoginRoute() {
    val service: TicTacToeService by inject()

    post("/user/login") {
        val credentials = call.getCredentialsOrRespondUnauthorized() ?: return@post
        val (login, password) = credentials

        // Проверяем пользователя
        val user = service.userRepository.getUserByLoginAndPassword(login, password)
        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid login or password")
            return@post
        }

        // Отправляем успешный ответ
        call.respond(HttpStatusCode.OK, UserMapper.fromDomain(user))
    }
}




suspend fun ApplicationCall.getCredentialsOrRespondUnauthorized(): Pair<String, String>? {
    val authHeader = request.headers["Authorization"]
    val credentials = AuthUtils.extractCredentials(authHeader)
    if (credentials == null) {
        respond(HttpStatusCode.Unauthorized, "Missing or invalid Authorization header")
    }
    return credentials
}




