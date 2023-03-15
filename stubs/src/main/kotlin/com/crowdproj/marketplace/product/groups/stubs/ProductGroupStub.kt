package com.crowdproj.marketplace.product.groups.stubs

import com.crowdproj.marketplace.product.groups.common.models.ProductGroup
import com.crowdproj.marketplace.product.groups.stubs.ProductGroupStubTestGroup.TEST_PRODUCT_GROUP1

object ProductGroupStub {
    fun get(): ProductGroup = TEST_PRODUCT_GROUP1.copy()

    fun prepareResult(block: ProductGroup.() -> Unit): ProductGroup = get().apply(block)
}