package com.crowdproj.marketplace.product.group.logging.common

import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@Suppress("unused")
interface ILogWrapper {
    val loggerId: String

    suspend fun log(
        msg: String = "",
        lvl: LogLevel = LogLevel.TRACE,
        marker: String = "DEV",
        e: Throwable? = null,
        data: Any? = null,
        objs: Map<String, Any>? = null,
    )

    suspend fun error(
        msg: String = "",
        marker: String = "DEV",
        e: Throwable? = null,
        data: Any? = null,
        objs: Map<String, Any>? = null,
    ) = log(msg, LogLevel.ERROR, marker, e, data, objs)

    suspend fun info(
        msg: String = "",
        marker: String = "DEV",
        data: Any? = null,
        objs: Map<String, Any>? = null,
    ) = log(msg, LogLevel.INFO, marker, null, data, objs)

    suspend fun debug(
        msg: String = "",
        marker: String = "DEV",
        data: Any? = null,
        objs: Map<String, Any>? = null,
    ) = log(msg, LogLevel.DEBUG, marker, null, data, objs)

    @OptIn(ExperimentalTime::class)
    suspend fun <T> doWithLogging(
        id: String = "",
        level: LogLevel = LogLevel.INFO,
        block: suspend () -> T,
    ) : T = try {
        log("Started $loggerId $id", level)
        val (res, diftime) = measureTimedValue { block() }

        log(
            msg = "Finished $loggerId $id",
            lvl = level,
            objs = mapOf("metricHandleTime" to diftime.toIsoString())
        )
        res
    } catch (e: Throwable) {
        log(
            msg = "Failed $loggerId $id",
            lvl = LogLevel.ERROR,
            e = e,
        )
        throw e
    }

    suspend fun <T> doWithErrorLogging(
        id: String = "",
        throwRequired: Boolean = true,
        block: suspend () -> T
    ): T? = try {
        val result = block()
        result
    } catch (e: Throwable) {
        log(
            msg = "Failed $loggerId $id",
            lvl = LogLevel.ERROR,
            e = e
        )
        if (throwRequired) throw e else null
    }

    companion object {
        val DEFAULT = object: ILogWrapper {
            override val loggerId: String = "NONE"

            override suspend fun log(
                msg: String,
                lvl: LogLevel,
                marker: String,
                e: Throwable?,
                data: Any?,
                objs: Map<String, Any>?
            ) {
                val markerString = marker
                    .takeIf { it.isNotBlank() }
                    ?.let { " ($it)" }

                val args = listOfNotNull(
                    "${Clock.System.now().toString()} [${lvl.name}]$markerString: $msg",
                    e?.let { "${it.message ?: "Unknown reason"}:\n${it.stackTraceToString()}" },
                    data.toString(),
                    objs.toString(),
                )
                println(args.joinToString("\n"))
            }
        }
    }
}