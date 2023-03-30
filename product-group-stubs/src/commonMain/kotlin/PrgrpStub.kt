package ru.otus.otuskotlin.marketplace.stubs

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import ru.otus.otuskotlin.marketplace.stubs.PrgrpStubFasteners.PRGRP_FASTENERS


object PrgrpStub {
    fun get(): PrgrpGroup = PRGRP_FASTENERS.copy()

    fun prepareSearchList(filter: String) = listOf(
        prgrpGroup("d-666-01", filter),
        prgrpGroup("d-666-02", filter),
        prgrpGroup("d-666-03", filter),
        prgrpGroup("d-666-04", filter),
        prgrpGroup("d-666-05", filter),
        prgrpGroup("d-666-06", filter),
    )

    private fun prgrpGroup(id: String, filter: String) = get().copy(
        id = PrgrpGroupId(id),
        name = "$filter $id",
        description = "desc $filter $id",
    )
}
