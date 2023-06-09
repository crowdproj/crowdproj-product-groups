package com.crowdproj.marketplace.product.group.app.ktor.stubs

import com.crowdproj.marketplace.product.group.api.v1.apiV1Mapper
import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.app.ktor.helpers.testSettings
import com.crowdproj.marketplace.product.group.app.ktor.module
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals


class ProductGroupStubApiTest {
    @Test
    fun create() = testApplication {
        application { module(testSettings()) }
        val response = client.post("v1/group/create") {
            val requestObj = ProductGroupCreateRequest(
                requestId = "12345",
                group = ProductGroupCreateObject(
                    name = "Screws",
                    description = "THE COOLEST",
                ),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.group?.id)
    }

    @Test
    fun read() = testApplication {
        application { module(testSettings()) }
        val response = client.post("v1/group/read") {
            val requestObj = ProductGroupReadRequest(
                requestId = "12345",
                group = ProductGroupReadObject("666"),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.group?.id)
    }

    @Test
    fun update() = testApplication {
        application { module(testSettings()) }
        val response = client.post("v1/group/update") {
            val requestObj = ProductGroupUpdateRequest(
                requestId = "12345",
                group = ProductGroupUpdateObject(
                    id = "666",
                    name = "Screws",
                    description = "THE COOLEST",
                ),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupUpdateRequest>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.group?.id)
    }

    @Test
    fun delete() = testApplication {
        application { module(testSettings()) }
        val response = client.post("v1/group/delete") {
            val requestObj = ProductGroupDeleteRequest(
                requestId = "12345",
                group = ProductGroupDeleteObject(
                    id = "666",
                    lock = "123"
                ),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupDeleteResponse>(responseJson)

        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.group?.id)
    }

    @Test
    fun search() = testApplication {
        application { module(testSettings()) }
        val response = client.post("v1/group/search") {
            val requestObj = ProductGroupSearchRequest(
                requestId = "12345",
                productGroupFilter = ProductGroupSearchFilter(),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ProductGroupSearchResponse>(responseJson)

        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.groups?.first()?.id)
    }
}
