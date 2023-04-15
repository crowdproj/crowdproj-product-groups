package com.crowdproj.marketplace.product.group.common.models

data class PrgrpError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val description: String = "",
    val level: Level = Level.ERROR,
){

    @Suppress("unused")
    enum class Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}
