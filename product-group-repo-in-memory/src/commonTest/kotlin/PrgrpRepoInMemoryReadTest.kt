package com.crowdproj.marketplace.product.group.repo.inmemory

import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpReadTest

class PrgrpRepoInMemoryReadTest: RepoPrgrpReadTest() {
    override val repo = PrgrpInMemoryRepo(
        initObjects = initObjects,
    )
}