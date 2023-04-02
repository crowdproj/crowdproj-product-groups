package com.crowdproj.marketplace.product.group.api.v1.response

import com.crowdproj.marketplace.product.group.api.v1.IApiStrategy
import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupResponse


sealed interface IResponseStrategy: IApiStrategy<IProductGroupResponse> {
    companion object {
        val members = listOf(
            CreateResponseStrategy,
            ReadResponseStrategy,
            UpdateResponseStrategy,
            DeleteResponseStrategy,
            SearchResponseStrategy,
            InitResponseStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
