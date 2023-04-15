package com.crowdproj.marketplace.product.group.log.mapper

import com.crowdproj.marketplace.api.logs.models.*
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.*
import kotlinx.datetime.Clock

fun PrgrpContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "product-group",
    prgrp = toPrgrpLog(),
    errors = errors.map { it.toLog() },

)

fun PrgrpContext.toPrgrpLog(): PrgrpLogModel? {
    val prgrpNone = PrgrpGroup()
    return PrgrpLogModel(
        requestId = requestId.takeIf { it != PrgrpRequestId.NONE }?.asString(),
        requestPrgrp = groupRequest.takeIf { it != prgrpNone }?.toLog(),
        responsePrgrp = groupResponse.takeIf { it != prgrpNone }?.toLog(),
        responsePrgrps = groupsResponse
            .takeIf { it.isNotEmpty() }
            ?.filter { it != prgrpNone }
            ?.map { it.toLog() },
        requestFilter = groupFilterRequest.takeIf { it != PrgrpGroupFilter() }?.toLog(),
    ).takeIf { it != PrgrpLogModel() }
}

private fun PrgrpGroupFilter.toLog() = PrgrpFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != PrgrpUserId.NONE }?.asString(),
)

fun PrgrpError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun PrgrpGroup.toLog() = PrgrpLog(
    id = id.takeIf { it != PrgrpGroupId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != PrgrpUserId.NONE }?.asString(),
    properties = properties.takeIf { it.isNotEmpty() }?.mapTo(mutableSetOf()) { it.asString() },
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)