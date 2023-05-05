package com.crowdproj.marketplace.product.group.common.helpers

import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpState


fun Throwable.asPrgrpError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = PrgrpError(
    code = code,
    group = group,
    field = "",
    message = message,
    description = this.toString(),
)

fun PrgrpContext.addError(vararg error: PrgrpError) = errors.addAll(error)

fun PrgrpContext.fail(error: PrgrpError) {
    addError(error)
    state = PrgrpState.FAILING
}

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: PrgrpError.Level = PrgrpError.Level.ERROR
) = PrgrpError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)