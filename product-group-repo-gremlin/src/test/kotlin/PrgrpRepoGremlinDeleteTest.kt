package com.crowdproj.marketplace.product.group.repo.gremlin

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpDeleteTest

class PrgrpRepoGremlinDeleteTest: RepoPrgrpDeleteTest() {
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
    override val deleteSucc: PrgrpGroup by lazy { repo.initializedObjects[0] }
    override val deleteConc: PrgrpGroup by lazy { repo.initializedObjects[1] }
    override val notFoundId: PrgrpGroupId = PrgrpGroupId("#3100:0")
}