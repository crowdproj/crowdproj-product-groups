package com.crowdproj.marketplace.product.group.biz.repo


import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpIdRequest

fun CorChainDsl<PrgrpContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Delete product group by id from DB"
    on { state == PrgrpState.RUNNING }
    handle {
        val request = DbPrgrpIdRequest(prgrpRepoPrepare)
        val result = prgrpRepo.deletePrGroup(request)
        if (!result.isSuccess) {
            state = PrgrpState.FAILING
            errors.addAll(result.errors)
        }
        prgrpRepoDone = prgrpRepoRead
    }
}
