package simran.dev.routing

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import simran.dev.db.DatabaseConnection
import simran.dev.entities.NoteEntity
import simran.dev.models.Note
import simran.dev.models.NoteRequest
import simran.dev.models.NoteResponse

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

        post("/notes") {
            val noteRequest = call.receive<NoteRequest>()

            val result = db.insert(NoteEntity) {
                set(NoteEntity.note, noteRequest.note)
            }

            if(result == 1) {
                call.respond(HttpStatusCode.Created, NoteResponse(success = true, data = "Successfully created note"))
                return@post
            }
            call.respond(HttpStatusCode.BadRequest, NoteResponse(success = false, data = "Failed to create a note"))
        }
    }
}