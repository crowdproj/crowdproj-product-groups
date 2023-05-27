package com.crowdproj.marketplace.product.group.biz.general

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.models.PrgrpWorkMode

fun CorChainDsl<PrgrpContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Preparing response data for a clients request"
    on { workMode != PrgrpWorkMode.STUB }
    handle {
        groupResponse = prgrpRepoDone
        groupsResponse = prgrpsRepoDone
        state = when (val st = state) {
            PrgrpState.RUNNING -> PrgrpState.FINISHING
            else -> st
        }
    }
}