package com.crowdproj.marketplace.product.group.api.v1

import com.crowdproj.marketplace.product.group.api.v1.models.*

class ResponseConstants {

    companion object {
        private val error = Error(code = "code", group = "group", field = "field", title = "title", description = "description")

        val productGroupCreateResponse: IProductGroupResponse = ProductGroupCreateResponse(
            responseType = "create",
            requestId = "123",
            errors = listOf(error),
            result = ResponseResult.SUCCESS,
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupDeleteResponse: IProductGroupResponse = ProductGroupDeleteResponse(
            responseType = "delete",
            requestId = "123",
            result = ResponseResult.SUCCESS,
            errors = listOf(error),
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupUpdateResponse: IProductGroupResponse = ProductGroupUpdateResponse(
            responseType = "update",
            requestId = "123",
            result = ResponseResult.SUCCESS,
            errors = listOf(error),
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupReadResponse: IProductGroupResponse = ProductGroupReadResponse(
            responseType = "read",
            requestId = "123",
            result = ResponseResult.SUCCESS,
            errors = listOf(error),
            group = ProductGroupResponseObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupSearchResponse: IProductGroupResponse = ProductGroupSearchResponse(
            responseType = "search",
            requestId = "123",
            result = ResponseResult.SUCCESS,
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