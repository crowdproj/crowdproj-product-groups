package com.crowdproj.marketplace.product.group.app.ktor.plugins

import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.common.logging.LoggerProvider
import com.crowdproj.marketplace.product.group.fluentbit.FluentBitAppender
import com.crowdproj.marketplace.product.group.fluentbit.ILogAppender
import com.crowdproj.marketplace.product.group.logging.kermit.loggerKermit
import io.ktor.server.application.*


val FLUENT_BIT_APPENDER = FluentBitAppender(host = "fluent-bit", port = 24225)
lateinit var appender: ILogAppender

fun Application.initAppSettings(): PrgrpAppSettings = PrgrpAppSettings(
    corSettings = PrgrpCorSettings(
        loggerProvider = getLoggerProviderConf(FLUENT_BIT_APPENDER),
    ),
    processor = "processor"
)

fun Application.initAppTestSettings(): PrgrpAppSettings = PrgrpAppSettings(
    corSettings = PrgrpCorSettings(
        loggerProvider = getLoggerProviderConf(ILogAppender.LOG_STUB_APPENDER),
    ),
    processor = "processor"
)

fun Application.getLoggerProviderConf(logAppender: ILogAppender): LoggerProvider = LoggerProvider {
    appender = logAppender
    loggerKermit(it, logAppender)
}

suspend fun initAppenderSocketConnection() = appender.initialize()

fun closeAppenderSocketConnection() = appender.close()
