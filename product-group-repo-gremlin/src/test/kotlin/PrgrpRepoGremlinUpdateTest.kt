package com.crowdproj.marketplace.product.group.repo.gremlin

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.repo.tests.RepoPrgrpUpdateTest

class PrgrpRepoGremlinUpdateTest : RepoPrgrpUpdateTest() {
    override val repo: PrgrpGremlinRepo by lazy {
        PrgrpGremlinRepo(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            enableSsl = false,
            user = ArcadeDbContainer.username,
            pass = ArcadeDbContainer.password,
            initObjects = initObjects,
            initRepo = { g -> g.V().drop().iterate() },
            randomUuid = { lockNew.asString() },
        )
    }
    override val updateSucc: PrgrpGroup by lazy { repo.initializedObjects[0] }
    override val updateConc: PrgrpGroup by lazy { repo.initializedObjects[1] }
}