import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.log.mapper.toLog
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromContext() {
        val context = PrgrpContext(
            requestId = PrgrpRequestId("1234"),
            command = PrgrpCommand.CREATE,
            groupResponse = PrgrpGroup(
                name = "title",
                description = "desc",
            ),
            errors = mutableListOf(
                PrgrpError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = PrgrpState.RUNNING,
        )

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("product-group", log.source)
        assertEquals("1234", log.prgrp?.requestId)
        val error = log.errors?.firstOrNull()
        assertEquals("wrong title", error?.message)
        assertEquals("ERROR", error?.level)
    }
}