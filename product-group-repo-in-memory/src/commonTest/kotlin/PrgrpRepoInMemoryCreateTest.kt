package com.crowdproj.marketplace.product.group.repo.inmemory

import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpCreateTest

class PrgrpRepoInMemoryCreateTest: RepoPrgrpCreateTest() {
    override val repo = PrgrpInMemoryRepo(
        initObjects = initObjects,
        randomUUID = { lockNew.asString() }
    )
}