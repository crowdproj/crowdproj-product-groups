package com.crowdproj.marketplace.product.group.repo.inmemory

import com.benasher44.uuid.uuid4
import com.crowdproj.marketplace.product.group.common.helpers.errorRepoConcurrency
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import com.crowdproj.marketplace.product.group.common.repo.*
import com.crowdproj.marketplace.product.group.repo.inmemory.model.PrgrpEntity
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import models.PrgrpGroupLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class PrgrpInMemoryRepo (
    initObjects: List<PrgrpGroup> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUUID: () -> String = { uuid4().toString() },
) : IPrgrpRepository {
    private val cache = Cache.Builder<String, PrgrpEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(prgrpGroup: PrgrpGroup) {
        val entity = PrgrpEntity(prgrpGroup)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createPrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        val key = randomUUID()
        val prGroup = rq.prGroup.copy(id = PrgrpGroupId(key), lock = PrgrpGroupLock(randomUUID()))
        val entity = PrgrpEntity(prGroup)
        cache.put(key, entity)

        return DbPrgrpResponse(
            data = prGroup,
            isSuccess = true,
        )
    }

    override suspend fun readPrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        val key = rq.id.takeIf { it != PrgrpGroupId.NONE }?.asString() ?: return resultErrorEmptyId

        return cache.get(key)
            ?.let {
                DbPrgrpResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updatePrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        val key = rq.prGroup.id.takeIf { it != PrgrpGroupId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.prGroup.lock.takeIf { it != PrgrpGroupLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newPrGroup = rq.prGroup.copy(lock = PrgrpGroupLock(randomUUID()))
        val entity = PrgrpEntity(newPrGroup)

        return mutex.withLock {
            val oldPrGroup = cache.get(key)
            when {
                oldPrGroup == null -> resultErrorNotFound
                oldPrGroup.lock != oldLock -> DbPrgrpResponse(
                    data = oldPrGroup.toInternal(),
                    isSuccess = false,
                    errors = listOf(
                        errorRepoConcurrency(
                            expectedLock = PrgrpGroupLock(oldLock),
                            actualLock = oldPrGroup.lock?.let { PrgrpGroupLock(it) },
                        )
                    )
                )

                else -> {
                    cache.put(key, entity)
                    DbPrgrpResponse(
                        data = newPrGroup,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deletePrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        val key = rq.id.takeIf { it != PrgrpGroupId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != PrgrpGroupLock.NONE }?.asString() ?: return resultErrorEmptyLock

        return mutex.withLock {
            val oldPrGroup = cache.get(key)
            when {
                oldPrGroup == null -> resultErrorNotFound
                oldPrGroup.lock != oldLock -> DbPrgrpResponse(
                    data = oldPrGroup.toInternal(),
                    isSuccess = false,
                    errors = listOf(
                        errorRepoConcurrency(
                            expectedLock = PrgrpGroupLock(oldLock),
                            actualLock = oldPrGroup.lock?.let { PrgrpGroupLock(it) }
                        )
                    )
                )

                else -> {
                    cache.invalidate(key)
                    DbPrgrpResponse(
                        data = oldPrGroup.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }

    }

    override suspend fun searchPrGroup(rq: DbPrgrpFilterRequest): DbPrgrpsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != PrgrpUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let{
                    entry.value.name?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()

        return DbPrgrpsResponse(
            data = result,
            isSuccess = true,
        )
    }

    companion object {
        val resultErrorEmptyId = DbPrgrpResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                PrgrpError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbPrgrpResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                PrgrpError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbPrgrpResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                PrgrpError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}