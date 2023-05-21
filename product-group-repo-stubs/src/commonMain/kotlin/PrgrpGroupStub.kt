package com.crowdproj.marketplace.product.group.repo.stubs

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupPermissionClient
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId

object PrgrpGroupStub {
    fun get() = PrgrpGroup(
        id = PrgrpGroupId("666"),
        name = "Need screws",
        description = "Need screws",
        ownerId = PrgrpUserId("user-1"),
        permissionsClient = mutableSetOf(
            PrgrpGroupPermissionClient.READ,
            PrgrpGroupPermissionClient.UPDATE,
            PrgrpGroupPermissionClient.DELETE,
            PrgrpGroupPermissionClient.MAKE_VISIBLE_PUBLIC,
            PrgrpGroupPermissionClient.MAKE_VISIBLE_GROUP,
            PrgrpGroupPermissionClient.MAKE_VISIBLE_OWNER,
        )
    )

    fun prepareResult(block: PrgrpGroup.() -> Unit): PrgrpGroup = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        prgrpGroupSearch("d-666-01", filter),
        prgrpGroupSearch("d-666-02", filter),
        prgrpGroupSearch("d-666-03", filter),
        prgrpGroupSearch("d-666-04", filter),
        prgrpGroupSearch("d-666-05", filter),
        prgrpGroupSearch("d-666-06", filter),
    )

    private fun prgrpGroupSearch(id: String, filter: String) =
        prgrpGroup(get(), id = id, filter = filter)

    private fun prgrpGroup(base: PrgrpGroup, id: String, filter: String) = base.copy(
        id = PrgrpGroupId(id),
        name = "$filter $id",
        description = "desc $filter $id",
    )
}