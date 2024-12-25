package web.route

import datasource.repository.TicTacToeService
import domain.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.UserMapper
import java.util.concurrent.CopyOnWriteArrayList
import at.favre.lib.crypto.bcrypt.BCrypt
import web.model.UserDTO
import java.security.MessageDigest
import java.util.*

//fun Route.routeNewUser() {
//    val service: TicTacToeService by inject()
//
//    post("/user/new/{login}/{password}") {
//        val login = call.parameters["login"]
//        val password = call.parameters["password"]
//        if (login == null || password == null) {
//            call.respond(HttpStatusCode.BadRequest, "Invalid USER data")
//            return@post
//        }
//        val newUser = User(login, password, CopyOnWriteArrayList())
//        service.userRepository.saveUser(newUser)
//        call.respond(HttpStatusCode.Created, newUser.login)
//        return@post
//    }
//}

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

        if (password.length < 8) {
            call.respond(HttpStatusCode.BadRequest, "Password must be at least 8 characters")
            return@post
        }

        // Проверяем, существует ли пользователь
        val existingUser = service.userRepository.getUserByLogin(login, password)
        if (existingUser != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
            return@post
        }

        // Создаем пользователя
        val hashedPassword = hashPasswordBase64(password) // Хеширование пароля
        val newUser = User(login, hashedPassword, CopyOnWriteArrayList())
        service.userRepository.saveUser(newUser)

        // Возвращаем успешный ответ
        call.respond(HttpStatusCode.Created, mapOf("message" to "User created successfully"))
    }
}


fun hashPasswordBase64(password: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
    return Base64.getEncoder().encodeToString(bytes)
}


fun verifyPassword(password: String, hashedPassword: String): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
}

fun Route.routeGetUser() {
    val service: TicTacToeService by inject()

    get("/user/{login}/{password}") {
        val login = call.parameters["login"]
        val password = call.parameters["password"]
        if (login == null || password == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid USER data")
            return@get
        }
        val existUser = service.userRepository.getUserByLogin(login, password)
        if (existUser == null) {
            call.respond(HttpStatusCode.NotFound, "User not found")
            return@get
        } else {
            call.respond(UserMapper.fromDomain(existUser))
            return@get
        }
    }
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