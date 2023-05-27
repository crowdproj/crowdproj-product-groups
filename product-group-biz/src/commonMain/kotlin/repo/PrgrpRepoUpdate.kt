package com.crowdproj.marketplace.product.group.biz.repo

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import repo.DbPrgrpRequest


fun CorChainDsl<PrgrpContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == PrgrpState.RUNNING }
    handle {
        val request = DbPrgrpRequest(prgrpRepoPrepare)
        val result = prgrpRepo.updatePrGroup(request)
        val resultGroup = result.data
        if (result.isSuccess && resultGroup != null) {
            prgrpRepoDone = resultGroup
        } else {
            state = PrgrpState.FAILING
            errors.addAll(result.errors)
            prgrpRepoDone
        }
    }
}
