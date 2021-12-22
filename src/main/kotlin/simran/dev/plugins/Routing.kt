package simran.dev.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import simran.dev.routing.notesRoutes

fun Application.configureRouting() {

    routing {
        get("/") {
                call.respondText("Hello World!")
            }
    }
    notesRoutes()
}
