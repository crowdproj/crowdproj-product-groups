package com.crowdproj.marketplace.product.group.biz.repo

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState

fun CorChainDsl<PrgrpContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Preparing an object for saving in the DB"
    on { state == PrgrpState.RUNNING }
    handle {
        prgrpRepoRead = prgrpValidated.deepCopy()
        prgrpRepoPrepare = prgrpRepoRead
    }
}
