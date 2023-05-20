package com.crowdproj.marketplace.product.group.repo.inmemory.model

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import models.PrgrpGroupLock

data class PrgrpEntity(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val lock: String? = null,
) {
    constructor(model: PrgrpGroup) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = PrgrpGroup(
        id = id?.let { PrgrpGroupId(it) } ?: PrgrpGroupId.NONE,
        name = name ?: "",
        description = description ?: "",
        ownerId = ownerId?.let { PrgrpUserId(it) } ?: PrgrpUserId.NONE,
        lock = lock?.let { PrgrpGroupLock(it) } ?: PrgrpGroupLock.NONE,
    )
}