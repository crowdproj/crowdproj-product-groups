package com.crowdproj.marketplace.product.groups.stubs

import com.crowdproj.marketplace.product.groups.common.models.ProductGroup
import com.crowdproj.marketplace.product.groups.common.models.ProductGroupId
import com.crowdproj.marketplace.product.groups.common.models.ProductGroupPermissionClient

object ProductGroupStubTestGroup {
    val TEST_PRODUCT_GROUP1: ProductGroup
        get() = ProductGroup(
            id = ProductGroupId("7"),
            name = "Test group 1",
            description = "Description test group 1",
            properties = "17",
            deleted = false,
            permissionsClient = mutableSetOf(
                ProductGroupPermissionClient.READ,
                ProductGroupPermissionClient.UPDATE,
                ProductGroupPermissionClient.DELETE
            )
        )

}