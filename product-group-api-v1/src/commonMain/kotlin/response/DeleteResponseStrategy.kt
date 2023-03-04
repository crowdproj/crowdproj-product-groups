package com.crowdproj.marketplace.product.group.api.v1.response

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupResponse
import com.crowdproj.marketplace.product.group.api.v1.models.ProductGroupDeleteResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object DeleteResponseStrategy: IResponseStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IProductGroupResponse> = ProductGroupDeleteResponse::class
    override val serializer: KSerializer<out IProductGroupResponse> = ProductGroupDeleteResponse.serializer()
    override fun <T : IProductGroupResponse> fillDiscriminator(req: T): T {
        require(req is ProductGroupDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
