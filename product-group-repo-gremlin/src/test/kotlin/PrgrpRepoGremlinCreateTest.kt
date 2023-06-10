package com.crowdproj.marketplace.product.group.repo.gremlin

import com.crowdproj.marketplace.product.group.common.repo.IPrgrpRepository
import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpCreateTest
import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpSearchTest



class PrgrpRepoGremlinCreateTest : RepoPrgrpCreateTest() {
    override val repo: IPrgrpRepository by lazy {
        PrgrpGremlinRepo(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            enableSsl = false,
            user = ArcadeDbContainer.username,
            pass = ArcadeDbContainer.password,
            initObjects = RepoPrgrpSearchTest.initObjects,
            initRepo = { g -> g.V().drop().iterate() },
            randomUuid = { lockNew.asString() }
        )
    }
}