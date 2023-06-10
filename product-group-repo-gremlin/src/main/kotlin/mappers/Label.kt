package com.crowdproj.marketplace.product.group.repo.gremlin.mappers

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup

fun PrgrpGroup.label(): String? = this::class.simpleName