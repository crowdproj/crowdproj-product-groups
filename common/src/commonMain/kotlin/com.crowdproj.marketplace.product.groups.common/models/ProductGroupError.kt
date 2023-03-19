package com.crowdproj.marketplace.product.groups.common.models

data class ProductGroupError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null
)