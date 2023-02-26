package models

import kotlin.jvm.JvmInline

@JvmInline
value class ProductGroupId(private val id: String) {

    fun asString() = id

    companion object {
        val NONE = ProductGroupId("")
    }
}
