package com.crowdproj.marketplace.product.group.app.ktor.plugins

import com.crowdproj.marketplace.product.group.common.repo.IPrgrpRepository
import io.ktor.server.application.*


expect fun Application.getDatabaseConf(type: PrgrpDbType): IPrgrpRepository

enum class PrgrpDbType(val confName: String) {
    PROD("prod"), TEST("test")
}
