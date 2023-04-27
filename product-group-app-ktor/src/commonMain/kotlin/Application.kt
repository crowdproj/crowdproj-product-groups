package com.crowdproj.marketplace.product.group.app.ktor

import com.crowdproj.marketplace.product.group.app.ktor.plugins.initAppSettings
import com.crowdproj.marketplace.product.group.app.ktor.v1.v1Group
import com.crowdproj.marketplace.product.group.app.ktor.v1.wsHandler
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.marketplace.app.plugins.initPlugins

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module(appSettings: PrgrpAppSettings = initAppSettings()) {
    initPlugins()

    routing {
        route("v1") {
            v1Group(appSettings)
        }
        route("v1") {
            webSocket("/ws") {
                wsHandler()
            }
        }
    }
}

