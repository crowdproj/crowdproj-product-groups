package com.crowdproj.marketplace.product.group.biz.repo

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

fun CorChainDsl<PrgrpContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Preparing date for deleting from DB
    """.trimIndent()
    on { state == PrgrpState.RUNNING }
    handle {
        prgrpRepoPrepare = prgrpValidated.deepCopy()
    }
}
