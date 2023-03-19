package com.crowdproj.marketplace.product.groups.common.models

data class ProductGroup(
    var id: ProductGroupId = ProductGroupId.NONE,
    var name: String = "",
    var description: String = "",
    var properties: String = "",
    var deleted: Boolean = false,
    val permissionsClient: MutableSet<ProductGroupPermissionClient> = mutableSetOf()
)
