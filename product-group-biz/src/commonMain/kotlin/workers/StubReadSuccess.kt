package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import ru.otus.otuskotlin.marketplace.stubs.PrgrpStub

fun CorChainDsl<PrgrpContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == PrgrpStubs.SUCCESS && state == PrgrpState.RUNNING }
    handle {
        state = PrgrpState.FINISHING
        val stub = PrgrpStub.prepareResult {
            groupRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
        }
        groupResponse = stub
    }
}