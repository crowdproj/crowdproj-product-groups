package ru.otus.otuskotlin.marketplace.api.v2

import com.crowdproj.marketplace.product.groups.api.v1.apiV1Mapper
import com.crowdproj.marketplace.product.groups.api.v1.models.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {

    @Test
    fun serializeProductGroupCreateResponse() {
        val json = apiV1Mapper.encodeToString(ResponseConstants.productGroupCreateResponse)

        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"name\":\\s*\"group title\""))
        assertContains(json, Regex("\"description\":\\s*\"product group description\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
    }

    @Test
    fun serializeProductGroupDeleteResponse() {
        val json = apiV1Mapper.encodeToString(ResponseConstants.productGroupDeleteResponse)

        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"delete\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"name\":\\s*\"group title\""))
        assertContains(json, Regex("\"description\":\\s*\"product group description\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
    }

    @Test
    fun serializeProductGroupUpdateResponse() {
        val json = apiV1Mapper.encodeToString(ResponseConstants.productGroupUpdateResponse)

        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"update\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"name\":\\s*\"group title\""))
        assertContains(json, Regex("\"description\":\\s*\"product group description\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
    }

    @Test
    fun serializeProductGroupReadResponse() {
        val json = apiV1Mapper.encodeToString(ResponseConstants.productGroupReadResponse)

        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"read\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"name\":\\s*\"group title\""))
        assertContains(json, Regex("\"description\":\\s*\"product group description\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
    }

    @Test
    fun serializeProductGroupSearchResponse() {
        val json = apiV1Mapper.encodeToString(ResponseConstants.productGroupSearchResponse)

        println(json)

        assertContains(json, Regex("\"responseType\":\\s*\"search\""))
        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"name\":\\s*\"group title\""))
        assertContains(json, Regex("\"description\":\\s*\"product group description\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
    }

    @Test
    fun deserializeProductGroupRequests() {
        val groupResponseExpected = listOf(
            ResponseConstants.productGroupCreateResponse,
            ResponseConstants.productGroupDeleteResponse,
            ResponseConstants.productGroupUpdateResponse,
            ResponseConstants.productGroupReadResponse,
            ResponseConstants.productGroupSearchResponse,
        )

        val groupResponseJsonList = groupResponseExpected.map { apiV1Mapper.encodeToString(it) }

        val productGroupCreateObjectActual =
            apiV1Mapper.decodeFromString(groupResponseJsonList[0]) as ProductGroupCreateResponse
        val productGroupDeleteObjectActual =
            apiV1Mapper.decodeFromString(groupResponseJsonList[1]) as ProductGroupDeleteResponse
        val productGroupUpdateObjectActual =
            apiV1Mapper.decodeFromString(groupResponseJsonList[2]) as ProductGroupUpdateResponse
        val productGroupReadObjectActual =
            apiV1Mapper.decodeFromString(groupResponseJsonList[3]) as ProductGroupReadResponse
        val productGroupSearchObjectActual =
            apiV1Mapper.decodeFromString(groupResponseJsonList[4]) as ProductGroupSearchResponse

        val productGroupResponseObjectActualList = listOf(
            productGroupCreateObjectActual,
            productGroupDeleteObjectActual,
            productGroupUpdateObjectActual,
            productGroupReadObjectActual,
            productGroupSearchObjectActual
        )

        groupResponseExpected.forEachIndexed { idx, expectedRes -> assertEquals(expectedRes, productGroupResponseObjectActualList[idx]) }
    }
}
