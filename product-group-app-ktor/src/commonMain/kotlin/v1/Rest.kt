package com.crowdproj.marketplace.product.group.app.ktor.v1

import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Group(appSettings: PrgrpAppSettings) {
    val logger = appSettings.corSettings.loggerProvider.logger(Route::v1Group)

    route("group") {
        post("create") {
            call.createPrgrp(appSettings, logger)
        }
        post("read") {
            call.readPrgrp(appSettings, logger)
        }
        post("update") {
            call.updatePrgrp(appSettings, logger)
        }
        post("delete") {
            call.deletePrgrp(appSettings, logger)
        }
        post("search") {
            call.searchPrgrp(appSettings, logger)
        }
    }
}