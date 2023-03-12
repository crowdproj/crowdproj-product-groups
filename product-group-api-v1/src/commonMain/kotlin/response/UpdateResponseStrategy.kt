package com.crowdproj.marketplace.product.group.api.v1.response

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupResponse
import com.crowdproj.marketplace.product.group.api.v1.models.ProductGroupUpdateResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object UpdateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IProductGroupResponse> = ProductGroupUpdateResponse::class
    override val serializer: KSerializer<out IProductGroupResponse> = ProductGroupUpdateResponse.serializer()
    override fun <T : IProductGroupResponse> fillDiscriminator(req: T): T {
        require(req is ProductGroupUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
