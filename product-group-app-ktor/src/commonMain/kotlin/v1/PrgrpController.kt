package com.crowdproj.marketplace.product.group.app.ktor.v1

import com.crowdproj.marketplace.product.group.api.v1.apiV1Mapper
import com.crowdproj.marketplace.product.group.api.v1.models.*
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.mapper.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.stubs.PrgrpStub


suspend fun ApplicationCall.createPrgrp() {
    val request = apiV1Mapper.decodeFromString<ProductGroupCreateRequest>(receiveText())
    val context = PrgrpContext()
    context.fromTransport(request)
    context.groupResponse = PrgrpStub.get()
    respond(apiV1Mapper.encodeToString(context.toTransportCreate()))
}

suspend fun ApplicationCall.readPrgrp() {
    val request = apiV1Mapper.decodeFromString<ProductGroupReadRequest>(receiveText())
    val context = PrgrpContext()
    context.fromTransport(request)
    context.groupResponse = PrgrpStub.get()
    respond(apiV1Mapper.encodeToString(context.toTransportRead()))
}

suspend fun ApplicationCall.updatePrgrp() {
    val request = apiV1Mapper.decodeFromString<ProductGroupUpdateRequest>(receiveText())
    val context = PrgrpContext()
    context.fromTransport(request)
    context.groupResponse = PrgrpStub.get()
    respond(apiV1Mapper.encodeToString(context.toTransportUpdate()))
}

suspend fun ApplicationCall.deletePrgrp() {
    val request = apiV1Mapper.decodeFromString<ProductGroupDeleteRequest>(receiveText())
    val context = PrgrpContext()
    context.fromTransport(request)
    context.groupResponse = PrgrpStub.get()
    respond(apiV1Mapper.encodeToString(context.toTransportDelete()))
}

suspend fun ApplicationCall.searchPrgrp() {
    val request = apiV1Mapper.decodeFromString<ProductGroupSearchRequest>(receiveText())
    val context = PrgrpContext()
    context.fromTransport(request)
    context.groupsResponse.addAll(PrgrpStub.prepareSearchList("Screws"))
    respond(apiV1Mapper.encodeToString(context.toTransportSearch()))
}