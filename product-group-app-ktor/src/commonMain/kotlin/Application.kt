package com.crowdproj.marketplace.product.group.app.ktor

import com.crowdproj.marketplace.product.group.api.v1.apiV1Mapper
import com.crowdproj.marketplace.product.group.app.ktor.v1.v1Group
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*


fun Application.module() {
    install(Routing)

    install(ContentNegotiation) {
        json(apiV1Mapper)
    }

    routing {
        route("v1") {
            v1Group()
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 8080) {
        module()
    } .start(wait = true)
}
