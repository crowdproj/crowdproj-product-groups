package com.crowdproj.marketplace.product.group.common.models

data class PrgrpGroup(
    var id: PrgrpGroupId = PrgrpGroupId.NONE,
    var name: String = "",
    var description: String = "",
    var ownerId: PrgrpUserId = PrgrpUserId.NONE,
    var propertiesFkId: PrgrpPropertiesId = PrgrpPropertiesId.NONE,
    val permissionsClient: MutableSet<PrgrpGroupPermissionClient> = mutableSetOf()
)
