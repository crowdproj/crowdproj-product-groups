package ru.otus.otuskotlin.marketplace.api.v2

import com.crowdproj.marketplace.product.groups.api.v1.models.*

class RequestConstants {
    companion object {
        val productGroupCreateRequest: IProductGroupRequest = ProductGroupCreateRequest(
            requestType = "create",
            requestId = "123",
            debug = ProductGroupDebug(
                mode = ProductGroupRequestDebugMode.STUB,
                stub = ProductGroupRequestDebugStubs.BAD_NAME
            ),
            group = ProductGroupCreateObject(
                name = "group title",
                description = "product group description",
            )
        )

        val productGroupDeleteRequest: IProductGroupRequest = ProductGroupDeleteRequest(
            requestType = "delete",
            requestId = "123",
            debug = ProductGroupDebug(
                mode = ProductGroupRequestDebugMode.STUB,
                stub = ProductGroupRequestDebugStubs.BAD_NAME
            ),
            group = ProductGroupDeleteObject(
                id = "1234",
                lock = "yes",
            )
        )

        val productGroupUpdateRequest: IProductGroupRequest = ProductGroupUpdateRequest(
            requestType = "update",
            requestId = "123",
            debug = ProductGroupDebug(
                mode = ProductGroupRequestDebugMode.STUB,
                stub = ProductGroupRequestDebugStubs.BAD_NAME
            ),
            group = ProductGroupUpdateObject(
                name = "update group",
                description = "description",
                propertiesFkId = "1",
                id = "1234",
                lock = "yes",
            )
        )

        val productGroupReadRequest: IProductGroupRequest = ProductGroupReadRequest(
            requestType = "read",
            requestId = "123",
            debug = ProductGroupDebug(
                mode = ProductGroupRequestDebugMode.STUB,
                stub = ProductGroupRequestDebugStubs.BAD_NAME
            ),
            group = ProductGroupReadObject(
                id = "1234",
            )
        )

        val productGroupSearchRequest: IProductGroupRequest = ProductGroupSearchRequest(
            requestType = "search",
            requestId = "123",
            debug = ProductGroupDebug(
                mode = ProductGroupRequestDebugMode.STUB,
                stub = ProductGroupRequestDebugStubs.BAD_NAME
            ),
            productGroupFilter = ProductGroupSearchFilter(
                searchString = "TV",
            )
        )
    }
}