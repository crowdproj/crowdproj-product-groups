package com.crowdproj.marketplace.product.group.repo.tests

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import models.PrgrpGroupLock

abstract class BaseInitPrgrps(val op: String) : IInitObjects<PrgrpGroup> {

    open val lockOld: PrgrpGroupLock = PrgrpGroupLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: PrgrpGroupLock = PrgrpGroupLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: PrgrpUserId = PrgrpUserId("owner-123"),
        lock: PrgrpGroupLock = lockOld,
    ) = PrgrpGroup(
        id = PrgrpGroupId("ad-repo-$op-$suf"),
        name = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        lock = lock,
    )
}
