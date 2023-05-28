package inmemory

import com.crowdproj.marketplace.product.group.api.v1.apiV1Mapper
import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.app.ktor.helpers.testSettings
import com.crowdproj.marketplace.product.group.app.ktor.module
import com.crowdproj.marketplace.product.group.common.models.PrgrpGroupId
import com.crowdproj.marketplace.product.group.repo.inmemory.PrgrpInMemoryRepo
import com.crowdproj.marketplace.product.group.stubs.PrgrpStub
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import models.PrgrpGroupLock
import models.PrgrpVisibility
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PrgrpInMemoryApiTest {
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidSup = "10000000-0000-0000-0000-000000000003"
    private val initPrgrp = PrgrpStub.prepareResult {
        id = PrgrpGroupId(uuidOld)
        name = "abc"
        description = "abc"
        visibility = PrgrpVisibility.VISIBLE_PUBLIC
        lock = PrgrpGroupLock(uuidOld)
    }

    private val userId = initPrgrp.ownerId

    @Test
    fun create() = testApplication {
        application { module(testSettings(PrgrpInMemoryRepo(randomUUID = { uuidNew }))) }

        val createPrgrp = ProductGroupCreateObject(
            name = "Screw Group",
            description = "THE COOLEST",
        )

        val response = client.post("v1/group/create") {
            val requestObj = ProductGroupCreateRequest(
                requestId = "12345",
                group = createPrgrp,
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(uuidNew, responseObj.group?.id)
        assertEquals(createPrgrp.name, responseObj.group?.name)
        assertEquals(createPrgrp.description, responseObj.group?.description)
    }

    @Test
    fun read() = testApplication {
        val repo = PrgrpInMemoryRepo(initObjects = listOf(initPrgrp), randomUUID = { uuidNew })
        application {
            module(testSettings(repo))
        }

        val response = client.post("/v1/group/read") {
            val requestObj = ProductGroupReadRequest(
                requestId = "12345",
                group = ProductGroupReadObject(uuidOld),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.group?.id)
    }

    @Test
    fun update() = testApplication {
        val repo = PrgrpInMemoryRepo(initObjects = listOf(initPrgrp), randomUUID = { uuidNew })
        application {
            module(testSettings(repo))
        }

        val prgrpUpdate = ProductGroupUpdateObject(
            id = uuidOld,
            name = "Screw group",
            description = "THE COOLEST",
            lock = initPrgrp.lock.asString(),
        )

        val response = client.post("/v1/group/update") {
            val requestObj = ProductGroupUpdateRequest(
                requestId = "12345",
                group = prgrpUpdate,
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(prgrpUpdate.id, responseObj.group?.id)
        assertEquals(prgrpUpdate.name, responseObj.group?.name)
        assertEquals(prgrpUpdate.description, responseObj.group?.description)
    }

    @Test
    fun delete() = testApplication {
        val repo = PrgrpInMemoryRepo(initObjects = listOf(initPrgrp), randomUUID = { uuidNew })
        application {
            module(testSettings(repo))
        }

        val response = client.post("/v1/group/delete") {
            val requestObj = ProductGroupDeleteRequest(
                requestId = "12345",
                group = ProductGroupDeleteObject(
                    id = uuidOld,
                    lock = initPrgrp.lock.asString(),
                ),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.group?.id)
    }

    @Test
    fun search() = testApplication {
        val repo = PrgrpInMemoryRepo(initObjects = listOf(initPrgrp), randomUUID = { uuidNew })
        application {
            module(testSettings(repo))
        }

        val response = client.post("/v1/group/search") {
            val requestObj = ProductGroupSearchRequest(
                requestId = "12345",
                productGroupFilter = ProductGroupSearchFilter(),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.groups?.size)
        assertEquals(uuidOld, responseObj.groups?.first()?.id)
    }
}