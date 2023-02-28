import com.crowdproj.marketplace.product.groups.api.models.*
import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import com.crowdproj.marketplace.product.groups.common.models.*
import com.crowdproj.marketplace.product.groups.mappers.exceptions.UnknownRequestClass
import com.crowdproj.marketplace.product.groups.common.stubs.ProductGroupStubs

fun ProductGroupContext.fromTransport(request: IProductGroupRequest) = when (request) {
    is PgCreateRequest -> fromTransport(request)
    is PgReadRequest -> fromTransport(request)
    is PgUpdateRequest -> fromTransport(request)
    is PgDeleteRequest -> fromTransport(request)
    is PgSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun IProductGroupRequest?.requestId() = this?.requestId?.let { ProductGroupRequestId(it) } ?: ProductGroupRequestId.NONE
private fun String?.toPgId() = this?.let { ProductGroupId(it) } ?: ProductGroupId.NONE
private fun String?.toPgWithId() = ProductGroup(id = this.toPgId())

private fun PgDebug?.transportToWorkMode(): ProductGroupWorkMode = when (this?.mode) {
    PgRequestDebugMode.PROD -> ProductGroupWorkMode.PROD
    PgRequestDebugMode.TEST -> ProductGroupWorkMode.TEST
    PgRequestDebugMode.STUB -> ProductGroupWorkMode.STUB
    null -> ProductGroupWorkMode.PROD
}

private fun PgDebug?.transportToStubCase(): ProductGroupStubs = when (this?.stub) {
    PgRequestDebugStubs.SUCCESS -> ProductGroupStubs.SUCCESS
    PgRequestDebugStubs.NOT_FOUND -> ProductGroupStubs.NOT_FOUND
    PgRequestDebugStubs.BAD_ID -> ProductGroupStubs.BAD_ID
    PgRequestDebugStubs.BAD_NAME -> ProductGroupStubs.BAD_NAME
    PgRequestDebugStubs.BAD_DESCRIPTION -> ProductGroupStubs.BAD_DESCRIPTION
    PgRequestDebugStubs.BAD_PROPERTIES -> ProductGroupStubs.BAD_PROPERTIES
    PgRequestDebugStubs.CANNOT_DELETE -> ProductGroupStubs.CANNOT_DELETE
    PgRequestDebugStubs.BAD_SEARCH_STRING -> ProductGroupStubs.BAD_SEARCH_STRING
    null -> ProductGroupStubs.NONE
}

fun ProductGroupContext.fromTransport(request: PgCreateRequest) {
    command = ProductGroupCommand.CREATE
    requestId = request.requestId()
    pgRequest = request.pg?.toInternal() ?: ProductGroup()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductGroupContext.fromTransport(request: PgUpdateRequest) {
    command = ProductGroupCommand.UPDATE
    requestId = request.requestId()
    pgRequest = request.pg?.toInternal() ?: ProductGroup()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductGroupContext.fromTransport(request: PgReadRequest) {
    command = ProductGroupCommand.READ
    requestId = request.requestId()
    pgRequest = request.pg?.id.toPgWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductGroupContext.fromTransport(request: PgDeleteRequest) {
    command = ProductGroupCommand.DELETE
    requestId = request.requestId()
    pgRequest = request.pg?.id.toPgWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductGroupContext.fromTransport(request: PgSearchRequest) {
    command = ProductGroupCommand.SEARCH
    requestId = request.requestId()
    pgFilterRequest = request.pgFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun PgSearchFilter?.toInternal(): ProductGroupFilter = ProductGroupFilter(
    id = this?.id.toPgId(),
    name = this?.name ?: "",
    description = this?.description ?: "",
    properties = this?.property ?: "",
    deleted = this?.deleted ?: false
)

private fun PgCreateObject.toInternal(): ProductGroup = ProductGroup(
    name = this.name ?: "",
    description = this.description ?: "",
    properties = this.properties ?: "",
    deleted = this.deleted ?: false,
)

private fun PgUpdateObject.toInternal(): ProductGroup = ProductGroup(
    id = this.id.toPgId(),
    name = this.name ?: "",
    description = this.description ?: "",
    properties = this.properties ?: "",
    deleted = this.deleted ?: false,
)
