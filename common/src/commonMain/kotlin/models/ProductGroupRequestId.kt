package models

import kotlin.jvm.JvmInline

@JvmInline
value class ProductGroupRequestId(private val id: String) {

    fun asString() = id

    companion object {
        val NONE = ProductGroupRequestId("")
    }
}