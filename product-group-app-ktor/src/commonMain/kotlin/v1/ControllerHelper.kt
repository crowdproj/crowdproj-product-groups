package com.crowdproj.marketplace.product.group.app.ktor.v1

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupResponse
import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import com.crowdproj.marketplace.product.group.app.ktor.plugins.closeAppenderSocketConnection
import com.crowdproj.marketplace.product.group.app.ktor.plugins.initAppenderSocketConnection
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.helpers.asPrgrpError
import com.crowdproj.marketplace.product.group.common.logging.ILogWrapper
import com.crowdproj.marketplace.product.group.common.models.PrgrpCommand
import com.crowdproj.marketplace.product.group.common.models.PrgrpState
import com.crowdproj.marketplace.product.group.log.mapper.toLog
import com.crowdproj.marketplace.product.group.mapper.fromTransport
import com.crowdproj.marketplace.product.group.mapper.toTransportGroup
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock

suspend inline fun <reified Q : IProductGroupRequest, @Suppress("unused") reified R : IProductGroupResponse> ApplicationCall.processV1(
    appSettings: PrgrpAppSettings,
    logger: ILogWrapper,
    logId: String,
    command: PrgrpCommand? = null,
) {
    initAppenderSocketConnection()

    val ctx = PrgrpContext(
        timeStart = Clock.System.now(),
    )
       val processor = appSettings.processor
    try {
        logger.doWithLogging(id = logId) {
            val request = receive<Q>()
            ctx.fromTransport(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            // TODO("add business logic")
            // processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            respond(ctx.toTransportGroup())
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
            )
            ctx.state = PrgrpState.FAILING
            ctx.errors.add(e.asPrgrpError())
            // TODO("add business logic")
            // processor.exec(ctx)
            respond(ctx.toTransportGroup())
        }
    } finally {
        closeAppenderSocketConnection()
    }

}