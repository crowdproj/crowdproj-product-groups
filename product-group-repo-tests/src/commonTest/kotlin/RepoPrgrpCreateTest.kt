package com.crowdproj.marketplace.product.group.repo.tests

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpRequest
import com.crowdproj.marketplace.product.group.common.repo.IPrgrpRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.PrgrpGroupLock
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoPrgrpCreateTest {
    abstract val repo: IPrgrpRepository

    protected open val lockNew: PrgrpGroupLock = PrgrpGroupLock("20000000-0000-0000-0000-000000000002")

    private val createObject = PrgrpGroup(
        name = "create object",
        description = "create object description",
        ownerId = PrgrpUserId("owner-123"),
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createPrGroup(DbPrgrpRequest(createObject))
        val expected = createObject.copy(id = result.data?.id ?: PrgrpGroupId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.name, result.data?.name)
        assertEquals(expected.description, result.data?.description)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitPrgrps("create") {
        override val initObjects: List<PrgrpGroup> = emptyList()
    }
}