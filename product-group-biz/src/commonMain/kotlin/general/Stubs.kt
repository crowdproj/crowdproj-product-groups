package com.crowdproj.marketplace.product.group.biz.groups

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.models.PrgrpWorkMode

fun CorChainDsl<PrgrpContext>.stubs(title: String, block: CorChainDsl<PrgrpContext>.() -> Unit) =
    chain {
        block()
        this.title = title
        on { workMode == PrgrpWorkMode.STUB && state == PrgrpState.RUNNING }
    }
