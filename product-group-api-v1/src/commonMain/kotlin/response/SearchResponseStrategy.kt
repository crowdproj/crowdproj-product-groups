package com.crowdproj.marketplace.product.groups.api.v1.response

import com.crowdproj.marketplace.product.groups.api.v1.models.IProductGroupResponse
import com.crowdproj.marketplace.product.groups.api.v1.models.ProductGroupSearchResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object SearchResponseStrategy: IResponseStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IProductGroupResponse> = ProductGroupSearchResponse::class
    override val serializer: KSerializer<out IProductGroupResponse> = ProductGroupSearchResponse.serializer()
    override fun <T : IProductGroupResponse> fillDiscriminator(req: T): T {
        require(req is ProductGroupSearchResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
