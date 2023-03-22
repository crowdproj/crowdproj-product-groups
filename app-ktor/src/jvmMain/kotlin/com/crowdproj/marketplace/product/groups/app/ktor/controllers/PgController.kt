import com.crowdproj.marketplace.product.groups.api.models.*
import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import com.crowdproj.marketplace.product.groups.stubs.ProductGroupStub
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.createPg() {
    val request = receive<PgCreateRequest>()
    val context = ProductGroupContext()
    context.fromTransport(request)
    context.pgResponse = ProductGroupStub.get()
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readPg() {
    val request = receive<PgReadRequest>()
    val context = ProductGroupContext()
    context.fromTransport(request)
    context.pgResponse = ProductGroupStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updatePg() {
    val request = receive<PgUpdateRequest>()
    val context = ProductGroupContext()
    context.fromTransport(request)
    context.pgResponse = ProductGroupStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.searchPg() {
    val request = receive<PgSearchRequest>()
    val context = ProductGroupContext()
    context.fromTransport(request)
    context.pgsResponse.addAll(ProductGroupStub.prepareSearchList("Test group 1"))
    respond(context.toTransportSearch())
}

suspend fun ApplicationCall.deletePg() {
    val request = receive<PgDeleteRequest>()
    val context = ProductGroupContext()
    context.fromTransport(request)
    context.pgResponse = ProductGroupStub.get()
    respond(context.toTransportDelete())
}