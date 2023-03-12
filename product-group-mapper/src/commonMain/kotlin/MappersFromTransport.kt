package com.crowdproj.marketplace.product.group.mapper

import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import com.crowdproj.marketplace.product.group.mapper.exceptions.UnknownRequestClass


fun PrgrpContext.fromTransport(request: IProductGroupRequest) = when (request) {
    is ProductGroupCreateRequest -> fromTransport(request)
    is ProductGroupReadRequest -> fromTransport(request)
    is ProductGroupUpdateRequest -> fromTransport(request)
    is ProductGroupDeleteRequest -> fromTransport(request)
    is ProductGroupSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class)
}

private fun String?.toGroupId() = this?.let { PrgrpGroupId(it) } ?: PrgrpGroupId.NONE
private fun String?.toGroupWithId() = PrgrpGroup(id = this.toGroupId())
private fun IProductGroupRequest?.requestId() = this?.requestId?.let { PrgrpRequestId(it) } ?: PrgrpRequestId.NONE
private fun String?.toPropertiesId() = this?.let { PrgrpPropertyId(it) } ?: PrgrpPropertyId.NONE

private fun ProductGroupDebug?.transportToWorkMode(): PrgrpWorkMode = when (this?.mode) {
    ProductGroupRequestDebugMode.PROD -> PrgrpWorkMode.PROD
    ProductGroupRequestDebugMode.TEST -> PrgrpWorkMode.TEST
    ProductGroupRequestDebugMode.STUB -> PrgrpWorkMode.STUB
    null -> PrgrpWorkMode.PROD
}

private fun ProductGroupDebug?.transportToStubCase(): PrgrpStubs = when (this?.stub) {
    ProductGroupRequestDebugStubs.SUCCESS -> PrgrpStubs.SUCCESS
    ProductGroupRequestDebugStubs.NOT_FOUND -> PrgrpStubs.NOT_FOUND
    ProductGroupRequestDebugStubs.BAD_ID -> PrgrpStubs.BAD_ID
    ProductGroupRequestDebugStubs.BAD_NAME -> PrgrpStubs.BAD_NAME
    ProductGroupRequestDebugStubs.BAD_DESCRIPTION -> PrgrpStubs.BAD_DESCRIPTION
    ProductGroupRequestDebugStubs.CANNOT_DELETE -> PrgrpStubs.CANNOT_DELETE
    ProductGroupRequestDebugStubs.BAD_SEARCH_STRING -> PrgrpStubs.BAD_SEARCH_STRING
    null -> PrgrpStubs.NONE
}

fun PrgrpContext.fromTransport(request: ProductGroupCreateRequest) {
    command = PrgrpCommand.CREATE
    requestId = request.requestId()
    groupRequest = request.group?.toInternal() ?: PrgrpGroup()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrgrpContext.fromTransport(request: ProductGroupReadRequest) {
    command = PrgrpCommand.READ
    requestId = request.requestId()
    groupRequest = request.group?.id.toGroupWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrgrpContext.fromTransport(request: ProductGroupUpdateRequest) {
    command = PrgrpCommand.UPDATE
    requestId = request.requestId()
    groupRequest = request.group?.toInternal() ?: PrgrpGroup()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrgrpContext.fromTransport(request: ProductGroupDeleteRequest) {
    command = PrgrpCommand.DELETE
    requestId = request.requestId()
    groupRequest = request.group?.id.toGroupWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PrgrpContext.fromTransport(request: ProductGroupSearchRequest) {
    command = PrgrpCommand.SEARCH
    requestId = request.requestId()
    groupFilterRequest = request.productGroupFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ProductGroupSearchFilter?.toInternal(): PrgrpGroupFilter = PrgrpGroupFilter(
    searchString = this?.searchString ?: ""
)

private fun ProductGroupCreateObject.toInternal(): PrgrpGroup = PrgrpGroup(
    name = this.name ?: "",
    description = this.description ?: "",
    properties = this.properties?.mapTo(mutableSetOf()){ it.toPropertiesId() } ?: mutableSetOf()
)

private fun ProductGroupUpdateObject.toInternal(): PrgrpGroup = PrgrpGroup(
    id = this.id.toGroupId(),
    name = this.name ?: "",
    description = this.description ?: "",
    properties = this.properties?.mapTo(mutableSetOf()){ it.toPropertiesId() } ?: mutableSetOf()
)

