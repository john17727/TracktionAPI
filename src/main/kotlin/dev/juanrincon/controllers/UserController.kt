package dev.juanrincon.controllers

import dev.juanrincon.API_VERSION
import dev.juanrincon.data.services.JwtService
import dev.juanrincon.data.services.UserService
import dev.juanrincon.data.state.ServiceResponse.Failed
import dev.juanrincon.data.state.ServiceResponse.Success
import dev.juanrincon.domain.models.ApiResponse
import dev.juanrincon.domain.models.StrongerSession
import dev.juanrincon.domain.models.User
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.routing.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*

const val USERS = "$API_VERSION/users"
const val USER_LOGIN = "$USERS/login"
const val USER_CREATE = "$USERS/create"

@KtorExperimentalLocationsAPI
@Location(USER_LOGIN)
class UserLoginRoute

@KtorExperimentalLocationsAPI
@Location(USER_CREATE)
class UserCreateRoute

@KtorExperimentalLocationsAPI
fun Route.userController(
    userService: UserService,
    jwtService: JwtService
) {
    post<UserCreateRoute> {
        val user = call.receive<User>()
        when (val response = userService.addNewUser(user)) {
            is Success -> {
                val currentUser = response.data
                call.sessions.set(StrongerSession(currentUser.id))
                call.respond(response.status, ApiResponse.success(jwtService.generateToken(currentUser))) // TODO: Change data to object
            }
            is Failed -> call.respond(response.status, ApiResponse.fail(response.message))
        }
    }

    post<UserLoginRoute> {
        val user = call.receive<User>()
        when (val response = userService.loginUser(user)) {
            is Success -> {
                val currentUser = response.data
                call.sessions.set(StrongerSession(currentUser.id))
                call.respond(response.status, ApiResponse.success(jwtService.generateToken(currentUser))) // TODO: Change data to object
            }
            is Failed -> call.respond(response.status, ApiResponse.fail(response.message))
        }
    }
}