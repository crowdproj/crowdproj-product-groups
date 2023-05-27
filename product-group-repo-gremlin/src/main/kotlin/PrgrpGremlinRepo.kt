package com.crowdproj.marketplace.product.group.repo.gremlin

import com.benasher44.uuid.uuid4
import com.crowdproj.marketplace.product.group.common.helpers.asPrgrpError
import com.crowdproj.marketplace.product.group.common.helpers.errorAdministration
import com.crowdproj.marketplace.product.group.common.helpers.errorRepoConcurrency
import com.crowdproj.marketplace.product.group.common.models.PrgrpError
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroup
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.common.models.PrgrpUserId
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_LOCK
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_NAME
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_OWNER_ID
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.FIELD_TMP_RESULT
import com.crowdproj.marketplace.product.group.repo.gremlin.PrgrpGremlinConst.RESULT_LOCK_FAILURE
import com.crowdproj.marketplace.product.group.repo.gremlin.exceptions.DbDuplicatedElementsException
import com.crowdproj.marketplace.product.group.repo.gremlin.mappers.addMkplPrgrp
import com.crowdproj.marketplace.product.group.repo.gremlin.mappers.label
import com.crowdproj.marketplace.product.group.repo.gremlin.mappers.listMkplPrgrp
import com.crowdproj.marketplace.product.group.repo.gremlin.mappers.toPrgrpGroup
import models.PrgrpGroupLock
import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.exception.ResponseException
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource
import org.apache.tinkerpop.gremlin.process.traversal.TextP
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as gr
import repo.*

class PrgrpGremlinRepo(
    private val hosts: String,
    private val port: Int = 8182,
    private val enableSsl: Boolean = false,
    private val user: String = "root",
    private val pass: String = "",
    initObjects: List<PrgrpGroup> = emptyList(),
    initRepo: ((GraphTraversalSource) -> Unit)? = null,
    val randomUuid: () -> String = { uuid4().toString() },
) : IPrgrpRepository {

    val initializedObjects: List<PrgrpGroup>

    private val cluster by lazy {
        Cluster.build().apply {
            addContactPoints(*hosts.split(Regex("\\s*,\\s*")).toTypedArray())
            port(port)
            credentials(user, pass)
            enableSsl(enableSsl)
        }.create()
    }
    private val g by lazy { AnonymousTraversalSource.traversal().withRemote(DriverRemoteConnection.using(cluster)) }

    init {
        if (initRepo != null) {
            initRepo(g)
        }
        initializedObjects = initObjects.map { save(it) }
    }

    override suspend fun createPrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        val key = randomUuid()
        val prgrp = rq.prGroup.copy(id = PrgrpGroupId(key), lock = PrgrpGroupLock(randomUuid()))
        val dbRes = try {
            g.addV(prgrp.label())
                .addMkplPrgrp(prgrp)
                .listMkplPrgrp()
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbPrgrpResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asPrgrpError())
            )
        }
        return when (dbRes.size) {
            0 -> resultErrorNotFound(key)
            1 -> DbPrgrpResponse(
                data = dbRes.first().toPrgrpGroup(),
                isSuccess = true
            )

            else -> errorDuplication(key)
        }
    }

    override suspend fun readPrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        val key = rq.id.takeIf { it != PrgrpGroupId.NONE }?.asString() ?: return resultErrorEmptyId
        val dbRes = try {
            g.V(key).listMkplPrgrp().toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbPrgrpResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asPrgrpError())
            )
        }

        return when (dbRes.size) {
            0 -> resultErrorNotFound(key)
            1 -> DbPrgrpResponse(
                data = dbRes.first().toPrgrpGroup(),
                isSuccess = true
            )

            else -> errorDuplication(key)
        }
    }

    override suspend fun updatePrGroup(rq: DbPrgrpRequest): DbPrgrpResponse {
        val key = rq.prGroup.id.takeIf { it != PrgrpGroupId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.prGroup.lock.takeIf { it != PrgrpGroupLock.NONE } ?: return resultErrorEmptyLock
        val newLock = PrgrpGroupLock(randomUuid())
        val newPrgrp = rq.prGroup.copy(lock = newLock)
        val dbRes = try {
            g.V(key)
                .`as`("a")
                .choose(
                    gr.select<Vertex, Any>("a")
                        .values<String>(FIELD_LOCK)
                        .`is`(oldLock.asString()),
                    gr.select<Vertex, Vertex>("a").addMkplPrgrp(newPrgrp).listMkplPrgrp(),
                    gr.select<Vertex, Vertex>("a").listMkplPrgrp(result = RESULT_LOCK_FAILURE)
                )
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key, e)
            }
            return DbPrgrpResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asPrgrpError())
            )
        }
        val prgrpResult = dbRes.firstOrNull()?.toPrgrpGroup()
        return when {
            prgrpResult == null -> resultErrorNotFound(key)
            dbRes.size > 1 -> errorDuplication(key)
            prgrpResult.lock != newLock -> DbPrgrpResponse(
                data = prgrpResult,
                isSuccess = false,
                errors = listOf(
                    errorRepoConcurrency(
                        expectedLock = oldLock,
                        actualLock = prgrpResult.lock
                    )
                ),
            )

            else -> DbPrgrpResponse(
                data = prgrpResult,
                isSuccess = true,
            )
        }
    }

    override suspend fun deletePrGroup(rq: DbPrgrpIdRequest): DbPrgrpResponse {
        val key = rq.id.takeIf { it != PrgrpGroupId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != PrgrpGroupLock.NONE } ?: return resultErrorEmptyLock
        val dbRes = try {
            g.V(key)
                .`as`("a")
                .choose(
                    gr.select<Vertex, Vertex>("a")
                        .values<String>(FIELD_LOCK)
                        .`is`(oldLock.asString()),
                    gr.select<Vertex, Vertex>("a")
                        .sideEffect(gr.drop<Vertex>())
                        .listMkplPrgrp(),
                    gr.select<Vertex, Vertex>("a")
                        .listMkplPrgrp(result = RESULT_LOCK_FAILURE)
                )
                .toList()
        } catch (e: Throwable) {
            if (e is ResponseException || e.cause is ResponseException) {
                return resultErrorNotFound(key)
            }
            return DbPrgrpResponse(
                data = null,
                isSuccess = false,
                errors = listOf(e.asPrgrpError())
            )
        }
        val dbFirst = dbRes.firstOrNull()
        val prgrpResult = dbFirst?.toPrgrpGroup()
        return when {
            prgrpResult == null -> resultErrorNotFound(key)
            dbRes.size > 1 -> errorDuplication(key)
            dbFirst[FIELD_TMP_RESULT] == RESULT_LOCK_FAILURE -> DbPrgrpResponse(
                data = prgrpResult,
                isSuccess = false,
                errors = listOf(
                    errorRepoConcurrency(
                        expectedLock = oldLock,
                        actualLock = prgrpResult.lock,
                    ),
                )
            )

            else -> DbPrgrpResponse(
                data = prgrpResult,
                isSuccess = true,
            )
        }
    }

    override suspend fun searchPrGroup(rq: DbPrgrpFilterRequest): DbPrgrpsResponse {
        val result = try {
            g.V()
                .apply { rq.ownerId.takeIf { it != PrgrpUserId.NONE }?.also { has(FIELD_OWNER_ID, it.asString()) } }
                .apply { rq.titleFilter.takeIf { it.isNotBlank() }?.also { has(FIELD_NAME, TextP.containing(it)) } }
                .listMkplPrgrp()
                .toList()
        } catch (e: Throwable) {
            return DbPrgrpsResponse(
                isSuccess = false,
                data = null,
                errors = listOf(e.asPrgrpError())
            )
        }
        return DbPrgrpsResponse(
            data = result.map { it.toPrgrpGroup() },
            isSuccess = true,
        )
    }

    private fun save(prgrp: PrgrpGroup): PrgrpGroup =
        g.addV(prgrp.label())
            .addMkplPrgrp(prgrp)
            .listMkplPrgrp()
            .next()
            ?.toPrgrpGroup()
            ?: throw RuntimeException("Cannot initialize object $prgrp")

    companion object {
        val resultErrorEmptyId = DbPrgrpResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                PrgrpError(
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
                    field = "lock",
                    message = "Lock must be provided"
                )
            )
        )

        fun resultErrorNotFound(key: String, e: Throwable? = null) = DbPrgrpResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                PrgrpError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found object with key $key",
                    description = e.toString()
                )
            )
        )

        fun errorDuplication(key: String) = DbPrgrpResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                errorAdministration(
                    violationCode = "duplicateObjects",
                    description = "Database consistency failure",
                    exception = DbDuplicatedElementsException("Db contains multiple elements for id = '$key'")
                )
            )
        )
    }

}