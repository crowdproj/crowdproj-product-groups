package com.crowdproj.marketplace.product.group.biz.groups

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

fun CorChainDsl<PrgrpContext>.operation(
    title: String,
    command: PrgrpCommand,
    block: CorChainDsl<PrgrpContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == PrgrpState.RUNNING }
}