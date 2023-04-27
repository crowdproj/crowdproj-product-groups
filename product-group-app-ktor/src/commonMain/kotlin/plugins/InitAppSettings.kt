package com.crowdproj.marketplace.product.group.app.ktor.plugins

import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import com.crowdproj.marketplace.product.group.common.PrgrpCorSettings
import com.crowdproj.marketplace.product.group.common.logging.LoggerProvider
import com.crowdproj.marketplace.product.group.logging.kermit.loggerKermit
import io.ktor.server.application.*


fun Application.initAppSettings(): PrgrpAppSettings = PrgrpAppSettings(
    corSettings = PrgrpCorSettings(
        loggerProvider = getLoggerProviderConf()
    ),
    processor = "processor"
)

fun Application.getLoggerProviderConf(): LoggerProvider = LoggerProvider { loggerKermit(it) }
