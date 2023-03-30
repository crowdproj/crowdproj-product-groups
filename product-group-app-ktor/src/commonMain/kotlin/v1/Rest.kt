package com.crowdproj.marketplace.product.group.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Group() {
    route("group") {
        post("create") {
            call.createPrgrp()
        }
        post("read") {
            call.readPrgrp()
        }
        post("update") {
            call.updatePrgrp()
        }
        post("delete") {
            call.deletePrgrp()
        }
        post("search") {
            call.searchPrgrp()
        }
    }
}