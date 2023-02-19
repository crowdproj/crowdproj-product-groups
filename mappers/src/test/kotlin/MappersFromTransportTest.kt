import models.ProductGroupWorkMode
import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import stubs.ProductGroupStubs
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MappersFromTransportTest {

    @Test
    fun fromTransportCreate() {
        val req = PgCreateRequest(
            requestId = "1234",
            debug = PgDebug(
                mode = PgRequestDebugMode.STUB,
                stub = PgRequestDebugStubs.SUCCESS,
            ),
            pg = PgCreateObject(
                name = "title",
                description = "desc",
                properties = "prop",
                deleted = false,
            ),
        )

        val context = ProductGroupContext()
        context.fromTransport(req)

        assertEquals(ProductGroupStubs.SUCCESS, context.stubCase)
        assertEquals(ProductGroupWorkMode.STUB, context.workMode)
        assertEquals("title", context.pgRequest.name)
        assertEquals("desc", context.pgRequest.description)
        assertEquals("prop", context.pgRequest.properties)
        assertFalse(context.pgRequest.deleted)
    }

    @Test
    fun fromTransportUpdate() {
        val req = PgUpdateRequest(
            requestId = "4321",
            debug = PgDebug(
                mode = PgRequestDebugMode.STUB,
                stub = PgRequestDebugStubs.BAD_NAME,
            ),
            pg = PgUpdateObject(
                name = "bad_name",
                description = "DescRIP",
                properties = "145",
                deleted = false,
            ),
        )

        val context = ProductGroupContext()
        context.fromTransport(req)

        assertEquals(ProductGroupStubs.BAD_NAME, context.stubCase)
        assertEquals(ProductGroupWorkMode.STUB, context.workMode)
        assertEquals("bad_name", context.pgRequest.name)
        assertEquals("DescRIP", context.pgRequest.description)
        assertEquals("145", context.pgRequest.properties)
        assertFalse(context.pgRequest.deleted)
    }

    @Test
    fun fromTransportDelete() {
        val req = PgDeleteRequest(
            requestId = "777",
            debug = PgDebug(
                mode = PgRequestDebugMode.STUB,
                stub = PgRequestDebugStubs.CANNOT_DELETE,
            ),
            pg = PgDeleteObject(
                id = "1456"
            ),
        )

        val context = ProductGroupContext()
        context.fromTransport(req)

        assertEquals(ProductGroupStubs.CANNOT_DELETE, context.stubCase)
        assertEquals(ProductGroupWorkMode.STUB, context.workMode)
        assertEquals("1456", context.pgRequest.id.asString())
    }
}