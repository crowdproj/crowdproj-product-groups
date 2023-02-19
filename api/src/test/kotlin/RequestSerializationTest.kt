package ru.otus.otuskotlin.marketplace.api.v1

import apiMapper
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = PgCreateRequest(
        requestId = "123",
        debug = PgDebug(
            mode = PgRequestDebugMode.STUB,
            stub = PgRequestDebugStubs.BAD_NAME
        ),
        pg = PgCreateObject(
            name = "ad title",
            description = "ad description",
            properties = "Prop!",
            deleted = true,
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)

        assertContains(json, Regex("\"name\":\\s*\"ad title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, IRequest::class.java) as PgCreateRequest

        assertEquals(request, obj)
    }
}
