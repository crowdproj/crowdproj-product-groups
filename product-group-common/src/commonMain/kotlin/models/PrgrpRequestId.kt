package com.crowdproj.marketplace.product.group.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class PrgrpRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = PrgrpRequestId("")
    }
}
