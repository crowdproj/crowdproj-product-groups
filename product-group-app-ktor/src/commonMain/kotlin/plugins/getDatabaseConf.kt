package com.crowdproj.marketplace.product.group.app.ktor.plugins

import io.ktor.server.application.*
import repo.IPrgrpRepository


expect fun Application.getDatabaseConf(type: PrgrpDbType): IPrgrpRepository

enum class PrgrpDbType(val confName: String) {
    PROD("prod"), TEST("test")
}
