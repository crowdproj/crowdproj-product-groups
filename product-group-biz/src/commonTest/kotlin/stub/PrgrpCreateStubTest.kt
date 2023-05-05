package com.crowdproj.marketplace.product.group.biz.stub

import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import com.crowdproj.marketplace.product.group.stubs.PrgrpStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PrgrpCreateStubTest {
    private val processor = PrgrpProcessor()

    val id = PrgrpGroupId("666")
    val title = "title 666"
    val description = "description 666"

    @Test
    fun create() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.CREATE,
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
        
        assertEquals(PrgrpStub.get().id, ctx.groupResponse.id)
        assertEquals(title, ctx.groupResponse.name)
        assertEquals(description, ctx.groupResponse.description)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.CREATE,
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
            command = PrgrpCommand.CREATE,
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
            command = PrgrpCommand.CREATE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.DB_ERROR,
            groupRequest = PrgrpGroup(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.CREATE,
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
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}