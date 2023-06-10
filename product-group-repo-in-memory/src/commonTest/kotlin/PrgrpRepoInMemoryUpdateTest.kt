package com.crowdproj.marketplace.product.group.repo.inmemory

import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpUpdateTest

class PrgrpRepoInMemoryUpdateTest : RepoPrgrpUpdateTest() {
    override val repo = PrgrpInMemoryRepo(
        initObjects = initObjects,
        randomUUID = { lockNew.asString() }
    )
}