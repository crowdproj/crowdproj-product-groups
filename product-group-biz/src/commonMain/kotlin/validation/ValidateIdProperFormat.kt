package com.crowdproj.marketplace.product.group.biz.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.helpers.errorValidation
import com.crowdproj.marketplace.product.group.common.helpers.fail
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId

fun CorChainDsl<PrgrpContext>.validateIdProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z-]+\$")
    on { prgrpValidating.id != PrgrpGroupId.NONE && ! prgrpValidating.id.asString().matches(regExp) }
    handle {
        /**
         * help prevent XSS attacks be encoding special HTML symbols
         */
        val encodedId = prgrpValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}