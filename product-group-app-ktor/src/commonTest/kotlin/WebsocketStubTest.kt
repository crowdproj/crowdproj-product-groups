package com.crowdproj.marketplace.product.group.app.ktor.stubs

import com.crowdproj.marketplace.product.group.api.v1.apiV1Mapper
import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.app.ktor.helpers.testSettings
import com.crowdproj.marketplace.product.group.app.ktor.module
import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@Ignore
class WebsocketStubTest {

    @Test
    fun createStub() {
        val request = ProductGroupCreateRequest(
            requestId = "12345",
            group = ProductGroupCreateObject(
                name = "Screw",
                description = "The COOOOOLEST",
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IProductGroupResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun readStub() {
        val request = ProductGroupReadRequest(
            requestId = "12345",
            group = ProductGroupReadObject("666"),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IProductGroupResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun updateStub() {
        val request = ProductGroupUpdateRequest(
            requestId = "12345",
            group = ProductGroupUpdateObject(
                id = "666",
                name = "Screw",
                description = "The COOOOLEST",
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IProductGroupResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun deleteStub() {
        val request = ProductGroupDeleteRequest(
            requestId = "12345",
            group = ProductGroupDeleteObject(
                id = "666",
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IProductGroupResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun searchStub() {
        val request = ProductGroupSearchRequest(
            requestId = "12345",
            productGroupFilter = ProductGroupSearchFilter(),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IProductGroupResponse>(request) {
            assertEquals("12345", it.requestId)
        }
    }

    private inline fun <reified T> testMethod(
        request: IProductGroupRequest,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        application { module(testSettings()) }
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/v1/ws") {
            withTimeout(3000) {
                val income = incoming.receive() as Frame.Text
                val response = apiV1Mapper.decodeFromString<T>(income.readText())
                assertIs<ProductGroupInitResponse>(response)
            }
            send(Frame.Text(apiV1Mapper.encodeToString(request)))
            withTimeout(3000) {
                val income = incoming.receive() as Frame.Text
                val text = income.readText()
                val response = apiV1Mapper.decodeFromString<T>(text)

                assertBlock(response)
            }
        }
    }
}
