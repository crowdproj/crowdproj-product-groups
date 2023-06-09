package com.crowdproj.marketplace.product.group.repo.tests

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.repo.DbPrgrpIdRequest
import com.crowdproj.marketplace.product.group.common.repo.IPrgrpRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoPrgrpDeleteTest {
    abstract val repo: IPrgrpRepository
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = PrgrpGroupId("ad-repo-delete-notFound")

    companion object : BaseInitPrgrps("delete") {
        override val initObjects: List<PrgrpGroup> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deletePrGroup(DbPrgrpIdRequest(id = deleteSucc.id, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.deletePrGroup(DbPrgrpIdRequest(id = notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val lockOld = deleteConc.lock
        val result = repo.deletePrGroup(DbPrgrpIdRequest(id = deleteConc.id, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }
}