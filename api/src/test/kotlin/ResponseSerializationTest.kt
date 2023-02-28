package ru.otus.otuskotlin.marketplace.api.v1

import apimappers.apiMapper
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = PgCreateResponse(
        requestId = "123",
        pg = PgResponseObject(
            name = "pg title",
            description = "pg description",
            properties = "Prop",
            deleted = true,
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(response)

        assertContains(json, """"name":"pg title"""")
        assertContains(json, """"properties":"Prop"""")
        assertContains(json, """"responseType":"create"""")
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(response)
        val obj = apiMapper.readValue(json, IResponse::class.java) as PgCreateResponse

        assertEquals(response, obj)
    }
}
