package com.crowdproj.marketplace.product.group.logging.kermit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import com.crowdproj.marketplace.product.group.common.logging.ILogWrapper
import com.crowdproj.marketplace.product.group.fluentbit.ILogAppender
import kotlin.reflect.KClass

@Suppress("unused")
fun loggerKermit(loggerId: String, ILogAppender: ILogAppender): ILogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )

    return LoggerWrapperKermit(
        logger = logger,
        loggerId = loggerId,
        logAppender = ILogAppender,
    )
}

@Suppress
fun loggerKermit(cls: KClass<*>, ILogAppender: ILogAppender): ILogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )

    return LoggerWrapperKermit(
        logger = logger,
        loggerId = cls.qualifiedName ?: "",
        logAppender = ILogAppender
    )
}