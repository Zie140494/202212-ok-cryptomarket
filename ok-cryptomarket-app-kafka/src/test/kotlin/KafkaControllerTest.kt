package ru.otus.otuskotlin.cryptomarket.app.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.cryptomarket.api.apiRequestSerialize
import ru.otus.otuskotlin.cryptomarket.api.apiResponseDeserialize
import ru.otus.otuskotlin.cryptomarket.api.models.*
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicIn
        val outputTopic = config.kafkaTopicOut

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyCpmk()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiRequestSerialize(
                        OrCreateRequest(
                        requestId = "11111111-1111-1111-1111-111111111111",
                        or = OrCreateObject(
                            accountNumber = "012345678901234567890",
                            walletNumber = "x0123456789",
                            fiatCurrency = FiatCurrency.RUB,
                            cryptoCurrency = CryptoCurrency.BTC,
                            action = Action.BUY
                        ),
                        debug = OrDebug(
                            mode = OrRequestDebugMode.STUB,
                            stub = OrRequestDebugStubs.SUCCESS
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
        val result = apiResponseDeserialize<OrCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
        assertEquals("012345678901234567890", result.or?.accountNumber)
    }

    companion object {
        const val PARTITION = 0
    }
}


