package com.crowdproj.marketplace.product.group.repo.inmemory

import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpDeleteTest

class PrgrpRepoInMemoryDeleteTest: RepoPrgrpDeleteTest() {
    override val repo = PrgrpInMemoryRepo(
        initObjects = initObjects,
    )
}