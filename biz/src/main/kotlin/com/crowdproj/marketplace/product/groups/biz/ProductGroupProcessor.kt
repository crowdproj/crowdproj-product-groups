package com.crowdproj.marketplace.product.groups.biz

import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import com.crowdproj.marketplace.product.groups.stubs.ProductGroupStub

class ProductGroupProcessor {

    suspend fun exec(ctx: ProductGroupContext) {
        ctx.pgResponse = ProductGroupStub.get()
    }
}