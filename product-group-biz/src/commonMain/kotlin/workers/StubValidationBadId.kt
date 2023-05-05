package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs

fun CorChainDsl<PrgrpContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { state == PrgrpState.RUNNING && stubCase == PrgrpStubs.BAD_ID }
    handle {
        state = PrgrpState.FAILING
        errors.add(
            PrgrpError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field",
            )
        )
    }
}