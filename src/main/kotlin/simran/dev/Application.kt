@file:Suppress("DEPRECATION")

package simran.dev

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
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

        val notes = database.from(NoteEntity)
            .select()

        for (row in notes) {
            println("${row[NoteEntity.id]} : ${row[NoteEntity.note]}")
        }


    }.start(wait = true)
}