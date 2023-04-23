package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs

fun CorChainDsl<PrgrpContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == PrgrpStubs.DB_ERROR && state == PrgrpState.RUNNING }
    handle {
        state = PrgrpState.FAILING
        errors.add(
            PrgrpError(
                group = "internal",
                code = "internal-db",
                message = "Internal-db"
            )
        )
    }
}