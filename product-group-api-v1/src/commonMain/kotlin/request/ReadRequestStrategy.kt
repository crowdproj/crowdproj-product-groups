package com.crowdproj.marketplace.product.group.api.v1.request

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.group.api.v1.models.ProductGroupReadRequest
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object ReadRequestStrategy: IRequestStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IProductGroupRequest> = ProductGroupReadRequest::class
    override val serializer: KSerializer<out IProductGroupRequest> = ProductGroupReadRequest.serializer()
    override fun <T : IProductGroupRequest> fillDiscriminator(req: T): T {
        require(req is ProductGroupReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
