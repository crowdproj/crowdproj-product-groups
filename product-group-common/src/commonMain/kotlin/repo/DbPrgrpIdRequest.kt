package com.crowdproj.marketplace.product.group.common.repo

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import models.PrgrpGroupLock

data class DbPrgrpIdRequest(
    val id: PrgrpGroupId,
    val lock: PrgrpGroupLock = PrgrpGroupLock.NONE,
) {
    constructor(productGroup: PrgrpGroup): this(productGroup.id, productGroup.lock)
}