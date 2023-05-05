package ru.otus.otuskotlin.marketplace.biz.validation.stub

import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PrgrpUpdateStubTest {

    private val processor = PrgrpProcessor()
    val id = PrgrpGroupId("777")
    val title = "title 666"
    val description = "desc 666"

    @Test
    fun create() = runTest {

        val ctx = PrgrpContext(
            command = PrgrpCommand.UPDATE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.SUCCESS,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.groupResponse.id)
        assertEquals(title, ctx.groupResponse.name)
        assertEquals(description, ctx.groupResponse.description)
    }

    @Test
    fun badId() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.UPDATE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_ID,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.UPDATE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_TITLE,
            groupRequest = PrgrpGroup(
                id = id,
                name = "",
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.UPDATE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_DESCRIPTION,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = "",
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.UPDATE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.DB_ERROR,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.UPDATE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_SEARCH_STRING,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
