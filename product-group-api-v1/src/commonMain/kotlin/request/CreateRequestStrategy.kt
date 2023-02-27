package com.crowdproj.marketplace.product.groups.api.v1.request

import com.crowdproj.marketplace.product.groups.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.groups.api.v1.models.ProductGroupCreateRequest
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object CreateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IProductGroupRequest> = ProductGroupCreateRequest::class
    override val serializer: KSerializer<out IProductGroupRequest> = ProductGroupCreateRequest.serializer()
    override fun <T : IProductGroupRequest> fillDiscriminator(req: T): T {
        require(req is ProductGroupCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
