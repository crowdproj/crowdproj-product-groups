package ru.otus.otuskotlin.marketplace.biz.validation.stub

import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class PrgrpSearchStubTest {

    private val processor = PrgrpProcessor()
    val filter = PrgrpGroupFilter(searchString = "bolt")

    @Test
    fun read() = runTest {

        val ctx = PrgrpContext(
            command = PrgrpCommand.SEARCH,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.SUCCESS,
            groupFilterRequest = filter,
        )
        processor.exec(ctx)

        assertTrue(ctx.groupsResponse.size > 1)
        val first = ctx.groupsResponse.firstOrNull() ?: fail("Empty response list")
        with(first) {
            assertTrue(name.contains(filter.searchString))
            assertTrue(description.contains(filter.searchString))
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.SEARCH,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_ID,
            groupFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.SEARCH,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.DB_ERROR,
            groupFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.SEARCH,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_TITLE,
            groupFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
