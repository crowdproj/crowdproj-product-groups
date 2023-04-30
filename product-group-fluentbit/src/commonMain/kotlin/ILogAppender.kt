package com.crowdproj.marketplace.product.group.fluentbit

import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.log.mapper.toLog
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


interface ILogAppender {
    suspend fun initialize()
    suspend fun send(message: String)
    fun close()

    companion object {
        val LOG_STUB_APPENDER = object : ILogAppender {
            override suspend fun initialize() {}

            override suspend fun send(message: String) {}

            override fun close() {}
        }
    }
}

class FluentBitAppender(
    val host: String,
    val port: Int
) : ILogAppender {

    private lateinit var socket: Socket
    private lateinit var channel: ByteWriteChannel
    private val ctx = PrgrpContext(
        timeStart = Clock.System.now(),
    )

    override suspend fun initialize() {
        socket = aSocket(SelectorManager(Dispatchers.Default)).tcp().connect(InetSocketAddress(host, port))
        channel = socket.openWriteChannel(autoFlush = true)
    }

    override suspend fun send(message: String) {
        val msg = Json.encodeToString(ctx.toLog(message))

        channel.writeStringUtf8(msg)
    }

    override fun close() {
        socket.close()
    }
}