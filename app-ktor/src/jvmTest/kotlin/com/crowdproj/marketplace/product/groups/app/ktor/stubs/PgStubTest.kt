package com.crowdproj.marketplace.product.groups.app.ktor.stubs

import com.crowdproj.marketplace.product.groups.api.models.*
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class PgStubTest {

    @Test
    fun create() = testApplication {
        val client = myClient()

        val response = client.post("/v1/pg/create") {
            val requestObj = PgCreateRequest(
                requestId = "12345",
                pg = PgCreateObject(
                    name = "pg title",
                    description = "pg description",
                    properties = "Prop!",
                    deleted = true,
                ),
                debug = PgDebug(
                    mode = PgRequestDebugMode.STUB,
                    stub = PgRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<PgCreateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
        assertEquals("7", responseObj.pg?.id)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()

        val response = client.post("/v1/pg/read") {
            val requestObj = PgReadRequest(
                requestId = "12345",
                pg = PgReadObject("7"),
                debug = PgDebug(
                    mode = PgRequestDebugMode.STUB,
                    stub = PgRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<PgReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("7", responseObj.pg?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()

        val response = client.post("/v1/pg/update") {
            val requestObj = PgUpdateRequest(
                requestId = "12345",
                pg = PgUpdateObject(
                    name = "pg title",
                    description = "pg description",
                    properties = "Prop!",
                    deleted = true,
                ),
                debug = PgDebug(
                    mode = PgRequestDebugMode.STUB,
                    stub = PgRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<PgUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("7", responseObj.pg?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()

        val response = client.post("/v1/pg/search") {
            val requestObj = PgSearchRequest(
                requestId = "12345",
                pgFilter = PgSearchFilter(),
                debug = PgDebug(
                    mode = PgRequestDebugMode.STUB,
                    stub = PgRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<PgSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("pg-7-01", responseObj.pgs?.first()?.id)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()

        val response = client.post("/v1/pg/delete") {
            val requestObj = PgDeleteRequest(
                requestId = "12345",
                pg = PgDeleteObject(
                    id = "7",
                ),
                debug = PgDebug(
                    mode = PgRequestDebugMode.STUB,
                    stub = PgRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<PgDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("7", responseObj.pg?.id)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}