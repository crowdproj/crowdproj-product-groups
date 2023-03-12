package com.crowdproj.marketplace.product.group.common.models

data class PrgrpError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
