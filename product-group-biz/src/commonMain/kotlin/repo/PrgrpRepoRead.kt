package com.crowdproj.marketplace.product.group.biz.repo

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpIdRequest


fun CorChainDsl<PrgrpContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Reading the product group from DB"
    on { state == PrgrpState.RUNNING }
    handle {
        val request = DbPrgrpIdRequest(prgrpValidated)
        val result = prgrpRepo.readPrGroup(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            prgrpRepoRead = resultAd
        } else {
            state = PrgrpState.FAILING
            errors.addAll(result.errors)
        }
    }
}
