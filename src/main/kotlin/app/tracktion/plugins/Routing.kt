package app.tracktion.plugins

import app.tracktion.controllers.exercisesController
import app.tracktion.controllers.typesController
import app.tracktion.controllers.userController
import app.tracktion.data.services.JwtService
import app.tracktion.di.MainModule
import app.tracktion.domain.interfaces.Repository
import app.tracktion.domain.models.User
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting(userRepository: Repository<User>, jwtService: JwtService) {
    val categoryService = MainModule.getCategoryService()
    val muscleService = MainModule.getMuscleService()
    val exerciseService = MainModule.getExerciseService()
    val userService = MainModule.getUserService(userRepository, environment)
    install(Resources)
    routing {
        typesController(categoryService, muscleService)
        exercisesController(exerciseService)
        userController(userService, jwtService)
        // Static plugin. Try to access `/static/index.html`
        static("/") {
            staticRootFolder = File("resources")
            static("api/gifs") {
                files("gifs")
            }
            static("api/images") {
                files("images")
            }
        }
    }
}
