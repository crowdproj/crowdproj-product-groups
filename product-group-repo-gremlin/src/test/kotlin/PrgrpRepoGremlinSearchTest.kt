package com.crowdproj.marketplace.product.group.repo.gremlin

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpSearchTest

class PrgrpRepoGremlinSearchTest: RepoPrgrpSearchTest() {
    override val repo: PrgrpGremlinRepo by lazy {
        PrgrpGremlinRepo(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            enableSsl = false,
            user = ArcadeDbContainer.username,
            pass = ArcadeDbContainer.password,
            initObjects = initObjects,
            initRepo = { g -> g.V().drop().iterate() },
        )
    }

    override val initializedObjects: List<PrgrpGroup> by lazy {
        repo.initializedObjects
    }
}