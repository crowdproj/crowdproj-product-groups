package com.crowdproj.marketplace.product.group.app.ktor.helpers

import PrgrpCorSettings
import com.crowdproj.marketplace.product.group.app.ktor.PrgrpAppSettings
import com.crowdproj.marketplace.product.group.repo.inmemory.PrgrpInMemoryRepo
import com.crowdproj.marketplace.product.group.repo.stubs.PrgrpRepoStub
import repo.IPrgrpRepository


fun testSettings(repo: IPrgrpRepository? = null) = PrgrpAppSettings(
    corSettings = PrgrpCorSettings(
        repoStub = PrgrpRepoStub(),
        repoTest = repo ?: PrgrpInMemoryRepo(),
        repoProd = repo ?: PrgrpInMemoryRepo(),
    ),
)
