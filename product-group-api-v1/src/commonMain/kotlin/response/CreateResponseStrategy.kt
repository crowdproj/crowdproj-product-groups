package com.crowdproj.marketplace.product.groups.api.v1.response

import com.crowdproj.marketplace.product.groups.api.v1.models.IProductGroupResponse
import com.crowdproj.marketplace.product.groups.api.v1.models.ProductGroupCreateResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object CreateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IProductGroupResponse> = ProductGroupCreateResponse::class
    override val serializer: KSerializer<out IProductGroupResponse> = ProductGroupCreateResponse.serializer()
    override fun <T : IProductGroupResponse> fillDiscriminator(req: T): T {
        require(req is ProductGroupCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
