package com.crowdproj.marketplace.product.groups.api
import com.crowdproj.marketplace.product.groups.api.models.IProductGroupRequest
import com.crowdproj.marketplace.product.groups.api.models.IProductGroupResponse
import com.fasterxml.jackson.databind.ObjectMapper

val apiMapper = ObjectMapper().apply {}

fun apiV1RequestSerialize(request: IProductGroupRequest): String = apiMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IProductGroupRequest> apiRequestDeserialize(json: String): T =
    apiMapper.readValue(json, IProductGroupRequest::class.java) as T

fun apiResponseSerialize(response: IProductGroupResponse): String = apiMapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IProductGroupResponse> apiV1ResponseDeserialize(json: String): T =
    apiMapper.readValue(json, IProductGroupResponse::class.java) as T