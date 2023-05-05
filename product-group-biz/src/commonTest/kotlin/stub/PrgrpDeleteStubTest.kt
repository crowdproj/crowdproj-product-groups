package ru.otus.otuskotlin.marketplace.biz.validation.stub

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
class PrgrpDeleteStubTest {

    private val processor = PrgrpProcessor()
    val id = PrgrpGroupId("666")
    val title = "Fasteners"
    val description = "description 666"

    @Test
    fun delete() = runTest {

        val ctx = PrgrpContext(
            command = PrgrpCommand.DELETE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.SUCCESS,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = description
            ),
        )
        processor.exec(ctx)

        with(PrgrpStub.get()) {
            assertEquals(id, ctx.groupResponse.id)
            assertEquals(name, ctx.groupResponse.name)
            assertEquals(description, ctx.groupResponse.description)
        }

    }

    @Test
    fun badId() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.DELETE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_ID,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = description
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = PrgrpContext(
            command = PrgrpCommand.DELETE,
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
            command = PrgrpCommand.DELETE,
            state = PrgrpState.NONE,
            workMode = PrgrpWorkMode.STUB,
            stubCase = PrgrpStubs.BAD_TITLE,
            groupRequest = PrgrpGroup(
                id = id,
                name = title,
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(PrgrpGroup(), ctx.groupResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
