package com.crowdproj.marketplace.product.groups.app.kafka

import ConsumerStrategy
import InputOutputTopics
import com.crowdproj.marketplace.product.groups.api.apiRequestDeserialize
import com.crowdproj.marketplace.product.groups.api.apiResponseSerialize
import com.crowdproj.marketplace.product.groups.api.models.IProductGroupRequest
import com.crowdproj.marketplace.product.groups.api.models.IProductGroupResponse
import com.crowdproj.marketplace.product.groups.common.ProductGroupContext
import fromTransport
import toTransport

class ConsumerStrategyV1 :  ConsumerStrategy{
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: ProductGroupContext): String {
        val response: IProductGroupResponse = source.toTransport()
        return apiResponseSerialize(response)
    }

    override fun deserialize(value: String, target: ProductGroupContext) {
        val request: IProductGroupRequest = apiRequestDeserialize(value)
        target.fromTransport(request)
    }
}