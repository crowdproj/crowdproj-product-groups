package com.crowdproj.marketplace.product.group.api.v1

import com.crowdproj.marketplace.product.group.api.v1.models.*

class RequestConstants {
    companion object {
        val productGroupCreateRequest: IProductGroupRequest = ProductGroupCreateRequest(
            requestType = "create",
            requestId = "123",
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.BAD_ID
            ),
            group = ProductGroupCreateObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupDeleteRequest: IProductGroupRequest = ProductGroupDeleteRequest(
            requestType = "delete",
            requestId = "123",
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.BAD_ID
            ),
            group = ProductGroupDeleteObject(
                id = "1234",
                lock = "yes",
            )
        )

        val productGroupUpdateRequest: IProductGroupRequest = ProductGroupUpdateRequest(
            requestType = "update",
            requestId = "123",
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.BAD_ID
            ),
            group = ProductGroupUpdateObject(
                name = "update group",
                description = "description",
                properties = setOf("1"),
                id = "1234",
                lock = "yes",
            )
        )

        val productGroupReadRequest: IProductGroupRequest = ProductGroupReadRequest(
            requestType = "read",
            requestId = "123",
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.BAD_ID
            ),
            group = ProductGroupReadObject(
                id = "1234",
            )
        )

        val productGroupSearchRequest: IProductGroupRequest = ProductGroupSearchRequest(
            requestType = "search",
            requestId = "123",
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.BAD_ID
            ),
            productGroupFilter = ProductGroupSearchFilter(
                searchString = "TV",
            )
        )
    }
}