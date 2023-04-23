package com.crowdproj.marketplace.product.group.biz.workers

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.common.stubs.PrgrpStubs
import ru.otus.otuskotlin.marketplace.stubs.PrgrpStub

fun CorChainDsl<PrgrpContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { state == PrgrpState.RUNNING && stubCase == PrgrpStubs.SUCCESS }
    handle {
        state = PrgrpState.FINISHING
        val stub = PrgrpStub.prepareResult {
            groupRequest.id.takeIf { it != PrgrpGroupId.NONE }?.also { this.id = it }
            groupRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            groupRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
        }

        groupResponse = stub
    }
}