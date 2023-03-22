import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.pg() {
    route("pg") {
        post("create") {
            call.createPg()
        }
        post("update") {
            call.updatePg()
        }
        post("read") {
            call.readPg()
        }
        post("search") {
            call.searchPg()
        }
        post("delete") {
            call.deletePg()
        }
    }
}