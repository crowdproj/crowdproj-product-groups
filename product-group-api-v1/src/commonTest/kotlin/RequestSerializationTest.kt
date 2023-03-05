package com.crowdproj.marketplace.product.group.api.v1

import com.crowdproj.marketplace.product.group.api.v1.models.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    @Test
    fun serializeProductGroupCreateRequest() {
        val json = apiV1Mapper.encodeToString(RequestConstants.productGroupCreateRequest)

        println(json)

        assertContains(json, Regex("\"name\":\\s*\"group title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"description\":\\s*\"product group description\""))
    }

    @Test
    fun serializeProductGroupDeleteRequest() {
        val json = apiV1Mapper.encodeToString(RequestConstants.productGroupDeleteRequest)

        println(json)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"delete\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"id\":\\s*\"1234\""))
        assertContains(json, Regex("\"lock\":\\s*\"yes\""))
    }

    @Test
    fun serializeProductGroupUpdateRequest() {
        val json = apiV1Mapper.encodeToString(RequestConstants.productGroupUpdateRequest)

        println(json)

        assertContains(json, Regex("\"requestType\":\\s*\"update\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"name\":\\s*\"update group\""))
        assertContains(json, Regex("\"description\":\\s*\"description\""))
        assertContains(json, Regex("\"propertiesFkId\":\\s*\"1\""))
        assertContains(json, Regex("\"id\":\\s*\"1234\""))
        assertContains(json, Regex("\"lock\":\\s*\"yes\""))

    }

    @Test
    fun serializeProductGroupReadRequest() {
        val json = apiV1Mapper.encodeToString(RequestConstants.productGroupReadRequest)

        println(json)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"read\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"id\":\\s*\"1234\""))

    }

    @Test
    fun serializeProductGroupSearchRequest() {
        val json = apiV1Mapper.encodeToString(RequestConstants.productGroupSearchRequest)

        println(json)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"search\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"searchString\":\\s*\"TV\""))

    }

    @Test
    fun deserializeProductGroupRequests() {
        val groupRequestsExpected = listOf(
            RequestConstants.productGroupCreateRequest,
            RequestConstants.productGroupDeleteRequest,
            RequestConstants.productGroupUpdateRequest,
            RequestConstants.productGroupReadRequest,
            RequestConstants.productGroupSearchRequest,
        )

        val groupRequestJsonList = groupRequestsExpected.map { apiV1Mapper.encodeToString(it) }

        val productGroupCreateObjectActual =
            apiV1Mapper.decodeFromString(groupRequestJsonList[0]) as ProductGroupCreateRequest
        val productGroupDeleteObjectActual =
            apiV1Mapper.decodeFromString(groupRequestJsonList[1]) as ProductGroupDeleteRequest
        val productGroupUpdateObjectActual =
            apiV1Mapper.decodeFromString(groupRequestJsonList[2]) as ProductGroupUpdateRequest
        val productGroupReadObjectActual =
            apiV1Mapper.decodeFromString(groupRequestJsonList[3]) as ProductGroupReadRequest
        val productGroupSearchObjectActual =
            apiV1Mapper.decodeFromString(groupRequestJsonList[4]) as ProductGroupSearchRequest

        val productGroupRequestObjectActualList = listOf(
            productGroupCreateObjectActual,
            productGroupDeleteObjectActual,
            productGroupUpdateObjectActual,
            productGroupReadObjectActual,
            productGroupSearchObjectActual
        )

        groupRequestsExpected.forEachIndexed { idx, expectedReq -> assertEquals(expectedReq, productGroupRequestObjectActualList[idx]) }
    }
}
