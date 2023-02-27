package com.crowdproj.marketplace.product.groups.api.v1.request

import com.crowdproj.marketplace.product.groups.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.groups.api.v1.models.ProductGroupSearchRequest
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object SearchRequestStrategy: IRequestStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IProductGroupRequest> = ProductGroupSearchRequest::class
    override val serializer: KSerializer<out IProductGroupRequest> = ProductGroupSearchRequest.serializer()
    override fun <T : IProductGroupRequest> fillDiscriminator(req: T): T {
        require(req is ProductGroupSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
