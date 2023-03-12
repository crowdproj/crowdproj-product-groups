package com.crowdproj.marketplace.product.group.mapper

import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import com.crowdproj.marketplace.product.group.mapper.exceptions.UnknownPrgrpCommand


fun PrgrpContext.toTransportGroup(): IProductGroupResponse = when (val cmd = command) {
    PrgrpCommand.CREATE -> toTransportCreate()
    PrgrpCommand.READ -> toTransportRead()
    PrgrpCommand.UPDATE -> toTransportUpdate()
    PrgrpCommand.DELETE -> toTransportDelete()
    PrgrpCommand.SEARCH -> toTransportSearch()
    PrgrpCommand.NONE -> throw UnknownPrgrpCommand(cmd)
}

fun PrgrpContext.toTransportCreate() = ProductGroupCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrgrpState.RUNNING) ProductGroupResponseResult.SUCCESS else ProductGroupResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    group = groupResponse.toTransportGroup()
)

fun PrgrpContext.toTransportRead() = ProductGroupReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrgrpState.RUNNING) ProductGroupResponseResult.SUCCESS else ProductGroupResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    group = groupResponse.toTransportGroup()
)

fun PrgrpContext.toTransportUpdate() = ProductGroupUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrgrpState.RUNNING) ProductGroupResponseResult.SUCCESS else ProductGroupResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    group = groupResponse.toTransportGroup()
)

fun PrgrpContext.toTransportDelete() = ProductGroupDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrgrpState.RUNNING) ProductGroupResponseResult.SUCCESS else ProductGroupResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    group = groupResponse.toTransportGroup()
)

fun PrgrpContext.toTransportSearch() = ProductGroupSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PrgrpState.RUNNING) ProductGroupResponseResult.SUCCESS else ProductGroupResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    groups = groupsResponse.toTransportGroup()
)

fun List<PrgrpGroup>.toTransportGroup(): List<ProductGroupResponseObject>? = this
    .map { it.toTransportGroup() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun PrgrpGroup.toTransportGroup(): ProductGroupResponseObject = ProductGroupResponseObject(
    id = id.takeIf { it != PrgrpGroupId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != PrgrpUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportGroup(),
    properties = properties.filter { it != PrgrpPropertyId.NONE }.mapTo(mutableSetOf()) { it.asString() }
)

private fun Set<PrgrpGroupPermissionClient>.toTransportGroup(): Set<ProductGroupPermissions>? = this
    .map { it.toTransportGroup() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun PrgrpGroupPermissionClient.toTransportGroup() = when (this) {
    PrgrpGroupPermissionClient.READ -> ProductGroupPermissions.READ
    PrgrpGroupPermissionClient.UPDATE -> ProductGroupPermissions.UPDATE
    PrgrpGroupPermissionClient.MAKE_VISIBLE_OWNER -> ProductGroupPermissions.MAKE_VISIBLE_OWN
    PrgrpGroupPermissionClient.MAKE_VISIBLE_GROUP -> ProductGroupPermissions.MAKE_VISIBLE_GROUP
    PrgrpGroupPermissionClient.MAKE_VISIBLE_PUBLIC -> ProductGroupPermissions.MAKE_VISIBLE_PUBLIC
    PrgrpGroupPermissionClient.DELETE -> ProductGroupPermissions.DELETE
}

private fun List<PrgrpError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportGroup() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun PrgrpError.toTransportGroup() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
