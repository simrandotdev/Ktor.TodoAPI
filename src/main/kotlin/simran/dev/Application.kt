@file:Suppress("DEPRECATION")

package simran.dev

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import simran.dev.entities.NoteEntity
import simran.dev.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}