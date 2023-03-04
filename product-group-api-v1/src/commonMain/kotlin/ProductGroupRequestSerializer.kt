package com.crowdproj.marketplace.product.group.api.v1

import com.crowdproj.marketplace.product.group.api.v1.models.IProductGroupRequest
import com.crowdproj.marketplace.product.group.api.v1.request.IRequestStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


val ProductGroupRequestSerializer = RequestSerializer(ProductGroupRequestSerializerBase)

private object ProductGroupRequestSerializerBase : JsonContentPolymorphicSerializer<IProductGroupRequest>(IProductGroupRequest::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IProductGroupRequest> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content

        return IRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IProductGroupRequest::class} implementation"
            )
    }
}

class RequestSerializer<T : IProductGroupRequest>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        IRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IRequest instance in RequestSerializer"
            )
}
