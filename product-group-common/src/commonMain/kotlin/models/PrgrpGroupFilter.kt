package com.crowdproj.marketplace.product.group.common.models

data class PrgrpGroupFilter(
    var searchString: String = "",
    var ownerId: PrgrpUserId = PrgrpUserId.NONE,
)
