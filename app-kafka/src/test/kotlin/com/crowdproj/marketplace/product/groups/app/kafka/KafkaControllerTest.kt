package com.crowdproj.marketplace.product.groups.app.kafka

import AppKafkaConsumer
import com.crowdproj.marketplace.product.groups.api.apiV1RequestSerialize
import com.crowdproj.marketplace.product.groups.api.apiV1ResponseDeserialize
import com.crowdproj.marketplace.product.groups.api.models.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class KafkaControllerTest {

    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(PgCreateRequest(
                        requestId = "11111111-1111-1111-1111-111111111111",
                        pg = PgCreateObject(
                            name = "Some Pg",
                            description = "some testing ad to check them all",
                            properties = "Prop",
                            deleted = false
                        ),
                        debug = PgDebug(
                            mode = PgRequestDebugMode.STUB,
                            stub = PgRequestDebugStubs.SUCCESS
                        )
                    )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<PgCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
        assertEquals("Test group 1", result.pg?.name)
    }

    companion object {
        const val PARTITION = 0
    }
}