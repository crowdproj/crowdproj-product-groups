package com.crowdproj.marketplace.product.group.api.v1.request

import com.crowdproj.marketplace.product.group.api.v1.IApiStrategy
import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupRequest

sealed interface IRequestStrategy: IApiStrategy<IProductGroupRequest> {
    companion object {

        private val members: List<IRequestStrategy> = listOf(
            CreateRequestStrategy,
            ReadRequestStrategy,
            UpdateRequestStrategy,
            DeleteRequestStrategy,
            SearchRequestStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
