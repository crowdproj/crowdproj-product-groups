package com.crowdproj.marketplace.product.group.mapper

import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    private fun stubContext(command: PrgrpCommand): PrgrpContext {
        return PrgrpContext(
            requestId = PrgrpRequestId("1234"),
            command = command,
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
            groupsResponse = mutableListOf(PrgrpGroup(
                name = "title",
                description = "desc",
            )),
            state = PrgrpState.RUNNING,
        )
    }

    @Test
    fun fromTransportCreateRequest() {
        val context = PrgrpContext()
        context.fromTransport(RequestConstants.productGroupCreateRequest)

        assertEquals(PrgrpStubs.BAD_NAME, context.stubCase)
        assertEquals(PrgrpWorkMode.STUB, context.workMode)
        assertEquals(PrgrpCommand.CREATE, context.command)
        assertEquals("123", context.requestId.asString())
        assertEquals("group title", context.groupRequest.name)
        assertEquals(PrgrpGroupId.NONE, context.groupRequest.id)
        assertEquals(PrgrpUserId.NONE, context.groupRequest.ownerId)
        assertEquals(PrgrpPropertiesId.NONE, context.groupRequest.propertiesFkId)
    }

    @Test
    fun fromTransportDeleteRequest() {
        val context = PrgrpContext()
        context.fromTransport(RequestConstants.productGroupDeleteRequest)

        assertEquals(PrgrpStubs.BAD_NAME, context.stubCase)
        assertEquals(PrgrpWorkMode.STUB, context.workMode)
        assertEquals(PrgrpCommand.DELETE, context.command)
        assertEquals("123", context.requestId.asString())
        assertEquals("", context.groupRequest.name)
        assertEquals("1234", context.groupRequest.id.asString())
        assertEquals(PrgrpUserId.NONE, context.groupRequest.ownerId)
        assertEquals(PrgrpPropertiesId.NONE, context.groupRequest.propertiesFkId)
    }

    @Test
    fun fromTransportUpdateRequest() {
        val context = PrgrpContext()
        context.fromTransport(RequestConstants.productGroupUpdateRequest)

        assertEquals(PrgrpStubs.BAD_NAME, context.stubCase)
        assertEquals(PrgrpWorkMode.STUB, context.workMode)
        assertEquals(PrgrpCommand.UPDATE, context.command)
        assertEquals("123", context.requestId.asString())
        assertEquals("update group", context.groupRequest.name)
        assertEquals("1234", context.groupRequest.id.asString())
        assertEquals(PrgrpUserId.NONE, context.groupRequest.ownerId)
        assertEquals("1", context.groupRequest.propertiesFkId.asString())
    }

    @Test
    fun fromTransportReadRequest() {
        val context = PrgrpContext()
        context.fromTransport(RequestConstants.productGroupReadRequest)

        assertEquals(PrgrpStubs.BAD_NAME, context.stubCase)
        assertEquals(PrgrpWorkMode.STUB, context.workMode)
        assertEquals(PrgrpCommand.READ, context.command)
        assertEquals("123", context.requestId.asString())
        assertEquals("", context.groupRequest.name)
        assertEquals("1234", context.groupRequest.id.asString())
        assertEquals(PrgrpUserId.NONE, context.groupRequest.ownerId)
        assertEquals(PrgrpPropertiesId.NONE, context.groupRequest.propertiesFkId)
    }

    @Test
    fun fromTransportSearchRequest() {
        val context = PrgrpContext()
        context.fromTransport(RequestConstants.productGroupSearchRequest)

        assertEquals(PrgrpStubs.BAD_NAME, context.stubCase)
        assertEquals(PrgrpWorkMode.STUB, context.workMode)
        assertEquals(PrgrpCommand.SEARCH, context.command)
        assertEquals("123", context.requestId.asString())
        assertEquals("", context.groupRequest.name)
        assertEquals(PrgrpGroupId.NONE, context.groupRequest.id)
        assertEquals(PrgrpUserId.NONE, context.groupRequest.ownerId)
        assertEquals(PrgrpPropertiesId.NONE, context.groupRequest.propertiesFkId)
    }

    @Test
    fun toTransportCreateResponse() {
        val context = stubContext(PrgrpCommand.CREATE)

        val resCreate = context.toTransportGroup() as ProductGroupCreateResponse

        assertEquals("1234", resCreate.requestId)
        assertEquals("title", resCreate.group?.name)
        assertEquals("desc", resCreate.group?.description)
        assertEquals(1, resCreate.errors?.size)
        assertEquals("err", resCreate.errors?.firstOrNull()?.code)
        assertEquals("request", resCreate.errors?.firstOrNull()?.group)
        assertEquals("title", resCreate.errors?.firstOrNull()?.field)
        assertEquals("wrong title", resCreate.errors?.firstOrNull()?.message)
    }

    @Test
    fun toTransportDeleteResponse() {
        val context = stubContext(PrgrpCommand.DELETE)

        val resDelete = context.toTransportGroup() as ProductGroupDeleteResponse

        assertEquals("1234", resDelete.requestId)
        assertEquals("title", resDelete.group?.name)
        assertEquals("desc", resDelete.group?.description)
        assertEquals(1, resDelete.errors?.size)
        assertEquals("err", resDelete.errors?.firstOrNull()?.code)
        assertEquals("request", resDelete.errors?.firstOrNull()?.group)
        assertEquals("title", resDelete.errors?.firstOrNull()?.field)
        assertEquals("wrong title", resDelete.errors?.firstOrNull()?.message)
    }

    @Test
    fun toTransportUpdateResponse() {
        val context = stubContext(PrgrpCommand.UPDATE)

        val resUpdate = context.toTransportGroup() as ProductGroupUpdateResponse

        assertEquals("1234", resUpdate.requestId)
        assertEquals("title", resUpdate.group?.name)
        assertEquals("desc", resUpdate.group?.description)
        assertEquals(1, resUpdate.errors?.size)
        assertEquals("err", resUpdate.errors?.firstOrNull()?.code)
        assertEquals("request", resUpdate.errors?.firstOrNull()?.group)
        assertEquals("title", resUpdate.errors?.firstOrNull()?.field)
        assertEquals("wrong title", resUpdate.errors?.firstOrNull()?.message)
    }

    @Test
    fun toTransportReadResponse() {
        val context = stubContext(PrgrpCommand.READ)

        val resRead = context.toTransportGroup() as ProductGroupReadResponse

        assertEquals("1234", resRead.requestId)
        assertEquals("title", resRead.group?.name)
        assertEquals("desc", resRead.group?.description)
        assertEquals(1, resRead.errors?.size)
        assertEquals("err", resRead.errors?.firstOrNull()?.code)
        assertEquals("request", resRead.errors?.firstOrNull()?.group)
        assertEquals("title", resRead.errors?.firstOrNull()?.field)
        assertEquals("wrong title", resRead.errors?.firstOrNull()?.message)
    }

    @Test
    fun toTransportSearchResponse() {
        val context = stubContext(PrgrpCommand.SEARCH)

        val resSearch = context.toTransportGroup() as ProductGroupSearchResponse

        assertEquals("1234", resSearch.requestId)
        assertEquals(1, resSearch.groups?.size)
        assertEquals("title", resSearch.groups?.firstOrNull()?.name)
        assertEquals("desc", resSearch.groups?.firstOrNull()?.description)
        assertEquals(1, resSearch.errors?.size)
        assertEquals("err", resSearch.errors?.firstOrNull()?.code)
        assertEquals("request", resSearch.errors?.firstOrNull()?.group)
        assertEquals("title", resSearch.errors?.firstOrNull()?.field)
        assertEquals("wrong title", resSearch.errors?.firstOrNull()?.message)
    }
}
