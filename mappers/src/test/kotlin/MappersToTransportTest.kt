import com.crowdproj.marketplace.product.groups.api.models.*
import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import com.crowdproj.marketplace.product.groups.common.models.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MappersToTransportTest {

    @Test
    fun toTransportCreate() {
        val context = ProductGroupContext(
            requestId = ProductGroupRequestId("1234"),
            command = ProductGroupCommand.CREATE,
            pgResponse = ProductGroup(
                name = "name",
                description = "desc",
                properties = "prop",
                deleted = false,
            ),
            errors = mutableListOf(
                ProductGroupError(
                    code = "err",
                    group = "request",
                    field = "name",
                    message = "wrong name",
                )
            ),
            state = ProductGroupState.RUNNING,
        )

        val req = context.toTransport() as PgCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("name", req.pg?.name)
        assertEquals("desc", req.pg?.description)
        assertEquals("prop", req.pg?.properties)
        req.pg?.deleted?.let { assertFalse(it) }
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("name", req.errors?.firstOrNull()?.field)
        assertEquals("wrong name", req.errors?.firstOrNull()?.message)
    }

    @Test
    fun toTransportUpdate() {
        val context = ProductGroupContext(
            requestId = ProductGroupRequestId("125"),
            command = ProductGroupCommand.UPDATE,
            pgResponse = ProductGroup(
                name = "name",
                description = "????",
                properties = "!!!!",
                deleted = false,
            ),
            state = ProductGroupState.RUNNING,
        )

        val req = context.toTransport() as PgUpdateResponse

        assertEquals("125", req.requestId)
        assertEquals("name", req.pg?.name)
        assertEquals("????", req.pg?.description)
        assertEquals("!!!!", req.pg?.properties)
        req.pg?.deleted?.let { assertFalse(it) }
        req.errors?.isEmpty()?.let { assertTrue(it) }
    }

    @Test
    fun toTransportDelete() {
        val context = ProductGroupContext(
            requestId = ProductGroupRequestId("125"),
            command = ProductGroupCommand.DELETE,
            pgResponse = ProductGroup(
                id = ProductGroupId("15"),
            ),
            errors = mutableListOf(
                ProductGroupError(
                    code = "err",
                    group = "request",
                    field = "id",
                    message = "wrong id",
                )
            ),
            state = ProductGroupState.RUNNING,
        )

        val req = context.toTransport() as PgDeleteResponse

        assertEquals("125", req.requestId)
        assertEquals("15", req.pg?.id)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("id", req.errors?.firstOrNull()?.field)
        assertEquals("wrong id", req.errors?.firstOrNull()?.message)
    }
}