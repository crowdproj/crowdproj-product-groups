import com.crowdproj.marketplace.product.groups.app.kafka.AppKafkaConfig
import com.crowdproj.marketplace.product.groups.app.kafka.ConsumerStrategyV1

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()))
    consumer.run()
}