package com.crowdproj.marketplace.product.group.api.v1.request

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.group.api.v1.models.ProductGroupDeleteRequest
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object DeleteRequestStrategy: IRequestStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IProductGroupRequest> = ProductGroupDeleteRequest::class
    override val serializer: KSerializer<out IProductGroupRequest> = ProductGroupDeleteRequest.serializer()
    override fun <T : IProductGroupRequest> fillDiscriminator(req: T): T {
        require(req is ProductGroupDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
