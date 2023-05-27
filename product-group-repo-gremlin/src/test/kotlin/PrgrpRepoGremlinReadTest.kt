package com.crowdproj.marketplace.product.group.repo.gremlin

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpReadTest

class PrgrpRepoGremlinReadTest : RepoPrgrpReadTest() {
    override val repo: PrgrpGremlinRepo by lazy {
        PrgrpGremlinRepo(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            user = ArcadeDbContainer.username,
            pass = ArcadeDbContainer.password,
            enableSsl = false,
            initObjects = initObjects,
            initRepo = { g -> g.V().drop().iterate() },
        )
    }
    override val readSucc: PrgrpGroup by lazy { repo.initializedObjects[0] }
}