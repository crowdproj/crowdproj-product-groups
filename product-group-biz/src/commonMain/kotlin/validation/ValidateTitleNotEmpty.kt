package com.crowdproj.marketplace.product.group.biz.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.helpers.errorValidation
import com.crowdproj.marketplace.product.group.common.helpers.fail

fun CorChainDsl<PrgrpContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { prgrpValidating.name.isEmpty() }
    handle {
        fail (
            errorValidation(
                field = "title",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}