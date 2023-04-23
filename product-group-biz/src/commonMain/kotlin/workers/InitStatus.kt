package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

fun CorChainDsl<PrgrpContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == PrgrpState.NONE }
    handle { state = PrgrpState.RUNNING }
}