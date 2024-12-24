package web.route

import datasource.repository.TicTacToeService
import domain.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import web.mapper.UserMapper

fun Route.routeNewUser() {
    val service: TicTacToeService by inject()

    post("/user/new/{login}/{password}") {
        val login = call.parameters["login"]
        val password = call.parameters["password"]
        if (login == null || password == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid USER data")
            return@post
        }
        val newUser = User(login, password)
        service.userRepository.saveUser(newUser)
        call.respond(HttpStatusCode.Created, newUser.login)
        return@post
    }
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