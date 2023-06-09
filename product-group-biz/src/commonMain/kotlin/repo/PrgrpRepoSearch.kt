package com.crowdproj.marketplace.product.group.biz.repo

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpFilterRequest

fun CorChainDsl<PrgrpContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Search for product group in the DB by filter"
    on { state == PrgrpState.RUNNING }
    handle {
        val request = DbPrgrpFilterRequest(
            titleFilter = prgrpFilterValidated.searchString,
            ownerId = prgrpFilterValidated.ownerId,
        )
        val result = prgrpRepo.searchPrGroup(request)
        val resultPrgrps = result.data
        if (result.isSuccess && resultPrgrps != null) {
            prgrpsRepoDone = resultPrgrps.toMutableList()
        } else {
            state = PrgrpState.FAILING
            errors.addAll(result.errors)
        }
    }
}
