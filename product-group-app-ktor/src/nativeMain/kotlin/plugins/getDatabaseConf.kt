package com.crowdproj.marketplace.product.group.app.ktor.plugins

import com.crowdproj.marketplace.product.group.repo.inmemory.PrgrpInMemoryRepo
import io.ktor.server.application.*
import repo.IPrgrpRepository


actual fun Application.getDatabaseConf(type: PrgrpDbType): IPrgrpRepository {
    return PrgrpInMemoryRepo()
}
