package com.crowdproj.marketplace.product.group.common.repo

import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId

data class DbPrgrpFilterRequest(
    val titleFilter: String = "",
    val ownerId: PrgrpUserId = PrgrpUserId.NONE,
)