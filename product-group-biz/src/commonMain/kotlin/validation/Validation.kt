package com.crowdproj.marketplace.product.group.biz.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

fun CorChainDsl<PrgrpContext>.validation(block: CorChainDsl<PrgrpContext>.() -> Unit) = chain {
    block()
    title = "validation"
    on { state == PrgrpState.RUNNING }
}