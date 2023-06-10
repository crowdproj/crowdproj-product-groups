package com.crowdproj.marketplace.product.group.app.ktor.plugins

import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.biz.PrgrpProcessor
import com.crowdproj.marketplace.product.group.fluentbit.FluentBitAppender
import com.crowdproj.marketplace.product.group.fluentbit.ILogAppender
import com.crowdproj.marketplace.product.group.logging.common.LoggerProvider
import com.crowdproj.marketplace.product.group.logging.kermit.loggerKermit
import com.crowdproj.marketplace.product.group.repo.stubs.PrgrpRepoStub
import io.ktor.server.application.*


val FLUENT_BIT_APPENDER = FluentBitAppender(host = "fluent-bit", port = 24225)
var appender: ILogAppender = ILogAppender.LOG_STUB_APPENDER

fun Application.initAppSettings(): PrgrpAppSettings {
    val corSettings = PrgrpCorSettings(
        loggerProvider = getLoggerProviderConf(FLUENT_BIT_APPENDER),
        repoTest = getDatabaseConf(PrgrpDbType.TEST),
        repoProd = getDatabaseConf(PrgrpDbType.PROD),
        repoStub = PrgrpRepoStub(),
    )

    return PrgrpAppSettings(
        corSettings = corSettings,
        processor = PrgrpProcessor(corSettings),
    )
}


fun Application.initAppTestSettings(): PrgrpAppSettings {
    val corSettings = PrgrpCorSettings(
        loggerProvider = getLoggerProviderConf(appender),
        repoTest = getDatabaseConf(PrgrpDbType.TEST),
        repoStub = PrgrpRepoStub(),
    )
    return PrgrpAppSettings(
        corSettings = corSettings,
        processor = PrgrpProcessor(corSettings),
    )
}


fun Application.getLoggerProviderConf(logAppender: ILogAppender): LoggerProvider = LoggerProvider {
    appender = logAppender
    loggerKermit(it, logAppender)
}

suspend fun initAppenderSocketConnection() = appender.initialize()

fun closeAppenderSocketConnection() = appender.close()
