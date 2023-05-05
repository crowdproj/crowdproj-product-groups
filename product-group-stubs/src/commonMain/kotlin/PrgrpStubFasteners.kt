package com.crowdproj.marketplace.product.group.stubs

import com.crowdproj.marketplace.product.group.common.models.*

object PrgrpStubFasteners {
    val PRGRP_FASTENERS: PrgrpGroup
        get() = PrgrpGroup(
            id = PrgrpGroupId("666"),
            name = "Fasteners",
            description = "Fasteners: Screws, axis and etc",
            ownerId = PrgrpUserId("user-1"),
            properties = mutableSetOf(PrgrpPropertyId("property-1")),
            permissionsClient = mutableSetOf(
                PrgrpGroupPermissionClient.READ,
                PrgrpGroupPermissionClient.UPDATE,
                PrgrpGroupPermissionClient.DELETE,
                PrgrpGroupPermissionClient.MAKE_VISIBLE_PUBLIC,
                PrgrpGroupPermissionClient.MAKE_VISIBLE_GROUP,
                PrgrpGroupPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
}
