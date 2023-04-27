package ru.otus.otuskotlin.marketplace.app.plugins

import com.crowdproj.marketplace.product.group.api.v1.apiV1Mapper
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Application.initPlugins() {
    install(ContentNegotiation) {
        json(apiV1Mapper)
    }
    install(Routing)
    install(WebSockets)

    install(CORS) {
        allowNonSimpleContentTypes = true
        allowSameOrigin = true
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader("*")

    }
    install(CachingHeaders)
    install(AutoHeadResponse)
}
