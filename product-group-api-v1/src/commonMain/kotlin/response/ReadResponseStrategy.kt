package com.crowdproj.marketplace.product.group.api.v1.response

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupResponse
import com.crowdproj.marketplace.product.group.api.v1.models.ProductGroupReadResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object ReadResponseStrategy: IResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IProductGroupResponse> = ProductGroupReadResponse::class
    override val serializer: KSerializer<out IProductGroupResponse> = ProductGroupReadResponse.serializer()
    override fun <T : IProductGroupResponse> fillDiscriminator(req: T): T {
        require(req is ProductGroupReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
