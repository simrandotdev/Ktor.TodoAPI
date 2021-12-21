@file:Suppress("DEPRECATION")

package simran.dev

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
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

        val database = Database.connect(
            url = "jdbc:mysql://localhost:3306/notes",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = "rootpassword"
        )

        database.update(NoteEntity) {
            set(it.note, "Study Ktor")
            where {
                it.id eq 1
            }
        }
        database.insert(NoteEntity) {
            set(it.note, "test note")
        }
        database.delete(NoteEntity) {
            it.note eq "test note"
        }


    }.start(wait = true)
}