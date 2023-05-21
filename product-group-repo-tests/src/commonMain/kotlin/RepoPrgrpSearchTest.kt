package com.crowdproj.marketplace.product.group.repo.tests

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import repo.DbPrgrpFilterRequest
import repo.IPrgrpRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoPrgrpSearchTest {
    abstract val repo: IPrgrpRepository

    protected open val initializedObjects: List<PrgrpGroup> = initObjects

    companion object: BaseInitPrgrps("search") {

        val searchOwnerId = PrgrpUserId("owner-124")
        override val initObjects: List<PrgrpGroup> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad4", ownerId = searchOwnerId),
        )
    }

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchPrGroup(DbPrgrpFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[2]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }
}