package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import com.crowdproj.marketplace.product.group.stubs.PrgrpStub

fun CorChainDsl<PrgrpContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == PrgrpStubs.SUCCESS && state == PrgrpState.RUNNING }
    handle {
        state = PrgrpState.FINISHING
        groupsResponse.addAll(PrgrpStub.prepareSearchList(groupFilterRequest.searchString))
    }
}