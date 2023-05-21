package com.crowdproj.marketplace.product.group.repo.tests

import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.PrgrpGroupLock
import repo.DbPrgrpRequest
import repo.IPrgrpRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoPrgrpUpdateTest {
    abstract val repo: IPrgrpRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = PrgrpGroupId("ad-repo-update-not-found")
    protected val lockBad = PrgrpGroupLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = PrgrpGroupLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        PrgrpGroup(
            id = updateSucc.id,
            name = "update object",
            description = "update object description",
            ownerId = PrgrpUserId("owner-123"),
            lock = initObjects.first().lock,
        )
    }

    private val reqUpdateNotFound = PrgrpGroup(
        id = updateIdNotFound,
        name = "update object not found",
        description = "update object not found description",
        ownerId = PrgrpUserId("owner-123"),
        lock = initObjects.first().lock,
    )

    private val reqUpdateConc by lazy {
        PrgrpGroup(
            id = updateConc.id,
            name = "update object not found",
            description = "update object not found description",
            ownerId = PrgrpUserId("owner-123"),
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updatePrGroup(DbPrgrpRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updatePrGroup(DbPrgrpRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updatePrGroup(DbPrgrpRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitPrgrps("update") {
        override val initObjects: List<PrgrpGroup> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}