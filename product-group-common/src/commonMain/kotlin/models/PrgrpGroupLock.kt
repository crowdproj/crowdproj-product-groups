package models

import kotlin.jvm.JvmInline

@JvmInline
value class PrgrpGroupLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = PrgrpGroupLock("")
    }
}