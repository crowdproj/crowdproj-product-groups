package com.crowdproj.marketplace.product.group.api.v1.request

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.group.api.v1.models.ProductGroupUpdateRequest
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object UpdateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IProductGroupRequest> = ProductGroupUpdateRequest::class
    override val serializer: KSerializer<out IProductGroupRequest> = ProductGroupUpdateRequest.serializer()
    override fun <T : IProductGroupRequest> fillDiscriminator(req: T): T {
        require(req is ProductGroupUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
