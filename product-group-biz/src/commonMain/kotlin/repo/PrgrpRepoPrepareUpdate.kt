package com.crowdproj.marketplace.product.group.biz.repo

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState


fun CorChainDsl<PrgrpContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Preparing the data for saving in DB: combining the data read from DB and the data received from the user"
    on { state == PrgrpState.RUNNING }
    handle {
        prgrpRepoPrepare = prgrpRepoRead.deepCopy().apply {
            this.name = prgrpValidated.name
            description = prgrpValidated.description
            visibility = prgrpValidated.visibility
        }
    }
}
