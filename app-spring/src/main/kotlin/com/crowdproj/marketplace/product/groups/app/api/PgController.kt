package com.crowdproj.marketplace.product.groups.app.api

import com.crowdproj.marketplace.product.groups.api.models.*
import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import com.crowdproj.marketplace.product.groups.stubs.ProductGroupStub
import fromTransport
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate

@RestController
@RequestMapping("v1/pg")
class PgController {

    @PostMapping("create")
    fun createPg(@RequestBody request: PgCreateRequest): PgCreateResponse {
        val context = ProductGroupContext()
        context.fromTransport(request)
        context.pgResponse = ProductGroupStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readPg(@RequestBody request: PgReadRequest): PgReadResponse {
        val context = ProductGroupContext()
        context.fromTransport(request)
        context.pgResponse = ProductGroupStub.get()
        return  context.toTransportRead()
    }

    @PostMapping("update")
    fun updatePg(@RequestBody request: PgUpdateRequest): PgUpdateResponse {
        val context = ProductGroupContext()
        context.fromTransport(request)
        context.pgResponse = ProductGroupStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deletePg(@RequestBody request: PgDeleteRequest): PgDeleteResponse {
        val context = ProductGroupContext()
        context.fromTransport(request)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchPg(@RequestBody request: PgSearchRequest): PgSearchResponse {
        val context = ProductGroupContext()
        context.fromTransport(request)
        context.pgsResponse.add(ProductGroupStub.get())
        return context.toTransportSearch()
    }
}