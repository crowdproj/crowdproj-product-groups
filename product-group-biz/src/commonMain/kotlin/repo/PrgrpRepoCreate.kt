package com.crowdproj.marketplace.product.group.biz.repo

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import repo.DbPrgrpRequest

fun CorChainDsl<PrgrpContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Add product group to DB"
    on { state == PrgrpState.RUNNING }
    handle {
        val request = DbPrgrpRequest(prgrpRepoPrepare)
        val result = prgrpRepo.createPrGroup(request)
        val resultPrgrp = result.data
        if (result.isSuccess && resultPrgrp != null) {
            prgrpRepoDone = resultPrgrp
        } else {
            state = PrgrpState.FAILING
            errors.addAll(result.errors)
        }
    }
}
