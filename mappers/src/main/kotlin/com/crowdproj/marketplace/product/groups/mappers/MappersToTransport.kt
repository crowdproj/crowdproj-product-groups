import com.crowdproj.marketplace.product.groups.api.models.*
import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import com.crowdproj.marketplace.product.groups.common.models.*
import com.crowdproj.marketplace.product.groups.mappers.exceptions.UnknownProductGroupCommand

fun ProductGroupContext.toTransport(): IProductGroupResponse = when (val cmd = command) {
    ProductGroupCommand.NONE -> throw UnknownProductGroupCommand(cmd)
    ProductGroupCommand.CREATE -> toTransportCreate()
    ProductGroupCommand.READ -> toTransportRead()
    ProductGroupCommand.UPDATE -> toTransportUpdate()
    ProductGroupCommand.DELETE -> toTransportDelete()
    ProductGroupCommand.SEARCH -> toTransportSearch()
}

fun ProductGroupContext.toTransportCreate() = PgCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductGroupState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    pg = pgResponse.toTransportPg()
)

fun ProductGroupContext.toTransportRead() = PgReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductGroupState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    pg = pgResponse.toTransportPg()
)

fun ProductGroupContext.toTransportUpdate() = PgUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductGroupState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    pg = pgResponse.toTransportPg()
)

fun ProductGroupContext.toTransportDelete() = PgDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductGroupState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    pg = pgResponse.toTransportPg()
)

fun ProductGroupContext.toTransportSearch() = PgSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductGroupState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    pgs = pgsResponse.toTransportPg()
)

fun List<ProductGroup>.toTransportPg(): List<PgResponseObject>? = this
    .map { it.toTransportPg() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ProductGroup.toTransportPg(): PgResponseObject = PgResponseObject(
    id = id.takeIf { it != ProductGroupId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    properties = properties.takeIf { it.isNotBlank() },
    permissions = permissionsClient.toTransportPg(),
)

private fun Set<ProductGroupPermissionClient>.toTransportPg(): Set<PgPermissions>? = this
    .map { it.toTransportPg() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun ProductGroupPermissionClient.toTransportPg() = when (this) {
    ProductGroupPermissionClient.READ -> PgPermissions.READ
    ProductGroupPermissionClient.UPDATE -> PgPermissions.UPDATE
    ProductGroupPermissionClient.DELETE -> PgPermissions.DELETE
}

private fun List<ProductGroupError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportPg() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ProductGroupError.toTransportPg() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)