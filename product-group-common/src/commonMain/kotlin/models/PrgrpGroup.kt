package com.crowdproj.marketplace.product.group.common.models

import com.crowdproj.marketplace.product.group.common.NONE
import kotlinx.datetime.Instant
import models.PrgrpGroupLock
import models.PrgrpVisibility

data class PrgrpGroup(
    var id: PrgrpGroupId = PrgrpGroupId.NONE,
    var name: String = "",
    var description: String = "",
    var ownerId: PrgrpUserId = PrgrpUserId.NONE,
    var properties: MutableSet<PrgrpPropertyId> = mutableSetOf(),
    var timePublished: Instant = Instant.NONE,
    var timeUpdated: Instant = Instant.NONE,
    var visibility: PrgrpVisibility = PrgrpVisibility.NONE,
    val permissionsClient: MutableSet<PrgrpGroupPermissionClient> = mutableSetOf(),
    var lock: PrgrpGroupLock = PrgrpGroupLock.NONE,
){
    fun deepCopy(): PrgrpGroup = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )
}
