package com.crowdproj.marketplace.product.groups.stubs

import com.crowdproj.marketplace.product.groups.common.models.ProductGroup
import com.crowdproj.marketplace.product.groups.common.models.ProductGroupId
import com.crowdproj.marketplace.product.groups.stubs.ProductGroupStubTestGroup.TEST_PRODUCT_GROUP1

object ProductGroupStub {
    fun get(): ProductGroup = TEST_PRODUCT_GROUP1.copy()

    fun prepareResult(block: ProductGroup.() -> Unit): ProductGroup = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        pgCreate("pg-7-01", filter, false),
        pgCreate("pg-7-02", filter, false),
        pgCreate("pg-7-03", filter, false),
        pgCreate("pg-7-04", filter, false),
        pgCreate("pg-7-05", filter, true),
        pgCreate("pg-7-06", filter, true),
    )

    private fun pgCreate(id: String, filter: String, deleted: Boolean) =
        productGroup(TEST_PRODUCT_GROUP1, id = id, filter = filter, deleted)

    private fun productGroup(base: ProductGroup, id: String, filter: String, deleted: Boolean) = base.copy(
        id = ProductGroupId(id),
        name = "$filter $id",
        description = "desc $filter $id",
        properties = "Prop!",
        deleted = deleted,
    )
}