package com.crowdproj.marketplace.product.group.common.models

import models.PrgrpGroupLock

data class PrgrpGroup(
    var id: PrgrpGroupId = PrgrpGroupId.NONE,
    var name: String = "",
    var description: String = "",
    var ownerId: PrgrpUserId = PrgrpUserId.NONE,
    var properties: MutableSet<PrgrpPropertyId> = mutableSetOf(),
    val permissionsClient: MutableSet<PrgrpGroupPermissionClient> = mutableSetOf(),
    var lock: PrgrpGroupLock = PrgrpGroupLock.NONE,
){
    fun deepCopy(): PrgrpGroup = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )
}
