package com.crowdproj.marketplace.product.group.app.ktor.v1

import com.crowdproj.marketplace.product.group.api.v1.apiV1Mapper
import com.crowdproj.marketplace.product.group.api.v1.encodeResponse
import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.helpers.addError
import com.crowdproj.marketplace.product.group.common.helpers.asPrgrpError
import com.crowdproj.marketplace.product.group.common.helpers.isUpdatableCommand
import com.crowdproj.marketplace.product.group.mapper.fromTransport
import com.crowdproj.marketplace.product.group.mapper.toTransportGroup
import com.crowdproj.marketplace.product.group.mapper.toTransportInit
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.decodeFromString
import ru.otus.otuskotlin.marketplace.stubs.PrgrpStub

val sessions = mutableSetOf<WebSocketSession>()
val mutex = Mutex()

suspend fun WebSocketSession.wsHandler() {

    mutex.withLock {
        sessions.add(this)
    }

    // Handle init request
    val ctx = PrgrpContext()

    val init = apiV1Mapper.encodeResponse(ctx.toTransportInit())
    outgoing.send(Frame.Text(init))

    // Handle flow
    incoming.receiveAsFlow().mapNotNull { it ->
        val frame = it as? Frame.Text ?: return@mapNotNull

        val jsonStr = frame.readText()
        val context = PrgrpContext()

        // Handle without flow destruction
        try {
            val request = apiV1Mapper.decodeFromString<IProductGroupRequest>(jsonStr)
            context.fromTransport(request)
            context.groupResponse = PrgrpStub.get()

            val result = apiV1Mapper.encodeResponse(context.toTransportGroup())

            // If change request, response is sent to everyone
            if (context.isUpdatableCommand()) {
                sessions.forEach {
                    it.send(Frame.Text(result))
                }
            } else {
                outgoing.send(Frame.Text(result))
            }
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        } catch (t: Throwable) {
            context.addError(t.asPrgrpError())

            val result = apiV1Mapper.encodeResponse(context.toTransportInit())
            outgoing.send(Frame.Text(result))
        }
    }.collect()
}
