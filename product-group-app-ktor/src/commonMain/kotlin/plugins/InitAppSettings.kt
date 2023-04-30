package com.crowdproj.marketplace.product.group.app.ktor.plugins

import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.common.logging.LoggerProvider
import com.crowdproj.marketplace.product.group.fluentbit.FluentBitAppender
import com.crowdproj.marketplace.product.group.logging.kermit.loggerKermit
import io.ktor.server.application.*


val FLUENT_BIT_APPENDER = FluentBitAppender(host = "fluent-bit", port = 24225)
fun Application.initAppSettings(): PrgrpAppSettings = PrgrpAppSettings(
    corSettings = PrgrpCorSettings(
        loggerProvider = getLoggerProviderConf(),
    ),
    processor = "processor"
)

fun Application.getLoggerProviderConf(): LoggerProvider = LoggerProvider {
    loggerKermit(it, FLUENT_BIT_APPENDER)
}

suspend fun initAppenderSocketConnection() = FLUENT_BIT_APPENDER.initialize()

fun closeAppenderSocketConnection() = FLUENT_BIT_APPENDER.close()
