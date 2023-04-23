package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.helpers.fail
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

fun CorChainDsl<PrgrpContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == PrgrpState.RUNNING }
    handle {
        fail(
            PrgrpError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}