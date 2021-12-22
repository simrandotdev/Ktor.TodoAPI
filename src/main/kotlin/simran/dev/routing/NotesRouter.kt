package simran.dev.routing

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import simran.dev.db.DatabaseConnection
import simran.dev.entities.NoteEntity
import simran.dev.models.Note

fun Application.notesRoutes() {
    val db = DatabaseConnection.database

    routing {
        get("/notes") {
            val notes = db.from(NoteEntity).select().map { querySet ->
                val id = querySet[NoteEntity.id]!!
                val note = querySet[NoteEntity.note]!!
                Note(id, note)
            }
            call.respond(notes)
        }
    }
}