package com.crowdproj.marketplace.product.group.common.helpers

import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.exceptions.RepoConcurrencyException
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import models.PrgrpGroupLock


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
fun errorAdministration(

field: String = "",
violationCode: String,
description: String,
exception: Exception? = null,
level: PrgrpError.Level = PrgrpError.Level.ERROR,
) = PrgrpError(
field = field,
code = "administration-$violationCode",
group = "administration",
message = "Microservice management error: $description",
level = level,
description = (exception ?: "Exception is empty").toString(),
)
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
fun errorRepoConcurrency(
    expectedLock: PrgrpGroupLock,
    actualLock: PrgrpGroupLock?,
    exception: Exception? = null,
) = PrgrpError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    description = (exception ?: RepoConcurrencyException(expectedLock, actualLock)).toString(),
)

val errorNotFound = PrgrpError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = PrgrpError(
    field = "id",
    message = "Id must not be null or blank"
)