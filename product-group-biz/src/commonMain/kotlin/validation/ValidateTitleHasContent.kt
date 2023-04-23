package com.crowdproj.marketplace.product.group.biz.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.helpers.errorValidation
import com.crowdproj.marketplace.product.group.common.helpers.fail

fun CorChainDsl<PrgrpContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { prgrpValidating.name.isNotEmpty() && !prgrpValidating.name.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "nocontent",
                description = "field must contain letters"
            )
        )
    }
}