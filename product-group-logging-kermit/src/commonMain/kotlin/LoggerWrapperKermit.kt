package com.crowdproj.marketplace.product.group.logging.kermit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.crowdproj.marketplace.product.group.common.logging.ILogWrapper
import com.crowdproj.marketplace.product.group.common.logging.LogLevel

class LoggerWrapperKermit(
    val logger: Logger,
    override val loggerId: String,
) : ILogWrapper {

    override fun log(msg: String,
                     lvl: LogLevel,
                     marker: String,
                     e: Throwable?,
                     data: Any?,
                     objs: Map<String, Any>?) {
        logger.log(
            severity = lvl.toKermit(),
            tag = marker,
            throwable = e,
            message = formatMessage(msg, data, objs)
        )
    }

    private fun LogLevel.toKermit() = when(this) {
        LogLevel.ERROR -> Severity.Error
        LogLevel.WARN -> Severity.Warn
        LogLevel.TRACE -> Severity.Verbose
        LogLevel.INFO -> Severity.Info
        LogLevel.DEBUG -> Severity.Debug
    }

    private inline fun formatMessage(
        msg: String = "",
        data: Any? = null,
        objs: Map<String, Any>? = null
    ): String {
        var message = msg
        data?.let {
            message += "\n" + it.toString()
        }
        objs?.forEach {
            message += "\n" + it.toString()
        }

        return message;
    }
}