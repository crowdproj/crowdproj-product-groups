package com.crowdproj.marketplace.product.group.common.logging

enum class LogLevel(
    private val levelInt: Int,
    private val levelStr: String
) {
    ERROR(40, "ERROR"),
    WARN(30, "WARN"),
    INFO(20,"INFO"),
    DEBUG(10, "DEBUG"),
    TRACE(0, "TRACE");

    @Suppress("unused")
    fun toInt(): Int {
        return levelInt
    }

    override fun toString(): String {
        return levelStr
    }
}