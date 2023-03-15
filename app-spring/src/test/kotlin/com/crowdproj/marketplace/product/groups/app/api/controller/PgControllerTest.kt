package com.crowdproj.marketplace.product.groups.app.api.controller

import com.crowdproj.marketplace.product.groups.api.models.*
import com.crowdproj.marketplace.product.groups.app.api.PgController
import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import com.crowdproj.marketplace.product.groups.stubs.ProductGroupStub
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportUpdate

@WebMvcTest(PgController::class)
internal class PgControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createPg() = testStubAd(
        "/v1/pg/create",
        PgCreateRequest(),
        ProductGroupContext().apply { pgResponse = ProductGroupStub.get() }.toTransportCreate()
    )

    @Test
    fun readPg() = testStubAd(
        "/v1/pg/read",
        PgReadRequest(),
        ProductGroupContext().apply { pgResponse = ProductGroupStub.get() }.toTransportRead()
    )

    @Test
    fun updatePg() = testStubAd(
        "/v1/pg/update",
        PgUpdateRequest(),
        ProductGroupContext().apply { pgResponse = ProductGroupStub.get() }.toTransportUpdate()
    )

    @Test
    fun deletePg() = testStubAd(
        "/v1/pg/delete",
        PgDeleteRequest(),
        ProductGroupContext().toTransportDelete()
    )

    private fun <Req : Any, Res : Any> testStubAd(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        val request = mapper.writeValueAsString(requestObj)
        val response = mapper.writeValueAsString(responseObj)

        mvc.perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }
}