package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import ru.otus.otuskotlin.marketplace.stubs.PrgrpStub

fun CorChainDsl<PrgrpContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    on { stubCase == PrgrpStubs.BAD_DESCRIPTION && state == PrgrpState.RUNNING }
    handle {
        state = PrgrpState.FAILING
        errors.add(
            PrgrpError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field",
            )
        )
    }
}