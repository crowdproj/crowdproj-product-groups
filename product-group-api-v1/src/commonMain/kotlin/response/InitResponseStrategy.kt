package com.crowdproj.marketplace.product.group.api.v1.response

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupResponse
import com.crowdproj.marketplace.product.group.api.v1.models.ProductGroupInitResponse
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

object InitResponseStrategy: IResponseStrategy {
    override val discriminator: String = "init"
    override val clazz: KClass<out IProductGroupResponse> = ProductGroupInitResponse::class
    override val serializer: KSerializer<out IProductGroupResponse> = ProductGroupInitResponse.serializer()
    override fun <T : IProductGroupResponse> fillDiscriminator(req: T): T {
        require(req is ProductGroupInitResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
