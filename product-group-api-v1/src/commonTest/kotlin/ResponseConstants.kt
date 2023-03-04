package ru.otus.otuskotlin.marketplace.api.v2

import com.crowdproj.marketplace.product.group.api.v1.models.*

class ResponseConstants {

    companion object {
        private val error = Error("code", "group", "field", "message")

        val productGroupCreateResponse: IProductGroupResponse = ProductGroupCreateResponse(
            responseType = "create",
            requestId = "123",
            errors = listOf(error),
            result = ProductGroupResponseResult.SUCCESS,
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupDeleteResponse: IProductGroupResponse = ProductGroupDeleteResponse(
            responseType = "delete",
            requestId = "123",
            result = ProductGroupResponseResult.SUCCESS,
            errors = listOf(error),
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupUpdateResponse: IProductGroupResponse = ProductGroupUpdateResponse(
            responseType = "update",
            requestId = "123",
            result = ProductGroupResponseResult.SUCCESS,
            errors = listOf(error),
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupReadResponse: IProductGroupResponse = ProductGroupReadResponse(
            responseType = "read",
            requestId = "123",
            result = ProductGroupResponseResult.SUCCESS,
            errors = listOf(error),
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupSearchResponse: IProductGroupResponse = ProductGroupSearchResponse(
            responseType = "search",
            requestId = "123",
            result = ProductGroupResponseResult.SUCCESS,
            errors = listOf(error),
            groups = listOf(
                ProductGroupResponseObject(
                    name = "group title",
                    description = "product group description",
                )
            )
        )
    }
}