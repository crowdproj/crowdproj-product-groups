package com.crowdproj.marketplace.product.group.repo.inmemory

import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpSearchTest

class PrgrpRepoInMemorySearchTest : RepoPrgrpSearchTest() {
    override val repo = PrgrpInMemoryRepo(
        initObjects = initObjects,
    )
}