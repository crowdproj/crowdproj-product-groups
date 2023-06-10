package com.crowdproj.marketplace.product.group.repo.tests

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpIdRequest
import com.crowdproj.marketplace.product.group.common.repo.IPrgrpRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoPrgrpReadTest {
    abstract val repo: IPrgrpRepository
    protected open val readSucc = initObjects[0]

    companion object : BaseInitPrgrps("read") {
        override val initObjects: List<PrgrpGroup> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = PrgrpGroupId("group-repo-read-notFound")

    }

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readPrGroup(DbPrgrpIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readPrGroup(DbPrgrpIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }
}