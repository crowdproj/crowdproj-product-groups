package com.crowdproj.marketplace.product.groups.api.v1

import com.crowdproj.marketplace.product.groups.api.v1.models.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IProductGroupRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is ProductGroupCreateRequest ->  RequestSerializer(ProductGroupCreateRequest.serializer()) as SerializationStrategy<IProductGroupRequest>
                is ProductGroupReadRequest   ->  RequestSerializer(ProductGroupReadRequest  .serializer()) as SerializationStrategy<IProductGroupRequest>
                is ProductGroupUpdateRequest ->  RequestSerializer(ProductGroupUpdateRequest.serializer()) as SerializationStrategy<IProductGroupRequest>
                is ProductGroupDeleteRequest ->  RequestSerializer(ProductGroupDeleteRequest.serializer()) as SerializationStrategy<IProductGroupRequest>
                is ProductGroupSearchRequest ->  RequestSerializer(ProductGroupSearchRequest.serializer()) as SerializationStrategy<IProductGroupRequest>
                else -> null
            }
        }
        polymorphicDefaultSerializer(IProductGroupResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is ProductGroupCreateResponse ->  ResponseSerializer(ProductGroupCreateResponse.serializer()) as SerializationStrategy<IProductGroupResponse>
                is ProductGroupReadResponse   ->  ResponseSerializer(ProductGroupReadResponse  .serializer()) as SerializationStrategy<IProductGroupResponse>
                is ProductGroupUpdateResponse ->  ResponseSerializer(ProductGroupUpdateResponse.serializer()) as SerializationStrategy<IProductGroupResponse>
                is ProductGroupDeleteResponse ->  ResponseSerializer(ProductGroupDeleteResponse.serializer()) as SerializationStrategy<IProductGroupResponse>
                is ProductGroupSearchResponse ->  ResponseSerializer(ProductGroupSearchResponse.serializer()) as SerializationStrategy<IProductGroupResponse>
                else -> null
            }
        }

        contextual(ProductGroupRequestSerializer)
        contextual(ProductGroupResponseSerializer)
    }
}
