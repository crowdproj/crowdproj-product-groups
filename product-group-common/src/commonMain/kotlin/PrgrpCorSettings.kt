package com.crowdproj.marketplace.product.group.common

import com.crowdproj.marketplace.product.group.common.logging.LoggerProvider

data class PrgrpCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
) {
    companion object {
        val NONE = PrgrpCorSettings()
    }
}