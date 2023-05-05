package com.crowdproj.marketplace.product.group.biz.validation

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

fun CorChainDsl<PrgrpContext>.finishPrgrpValidation(title: String) = worker {
    this.title = title
    on { state == PrgrpState.RUNNING }
    handle {
        prgrpValidated = prgrpValidating
    }
}

fun CorChainDsl<PrgrpContext>.finishPrgrpFilterValidation(title: String) = worker {
    this.title = title
    on { state == PrgrpState.RUNNING }
    handle {
        prgrpFilterValidated = prgrpFilterValidating
    }
}