package com.crowdproj.marketplace.product.group.common.helpers

import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpError


fun Throwable.asPrgrpError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = PrgrpError(
    code = code,
    group = group,
    field = "",
    title = message,
    description = this.toString(),
)

fun PrgrpContext.addError(vararg error: PrgrpError) = errors.addAll(error)
