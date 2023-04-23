package ru.otus.otuskotlin.cryptomarket.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategy(),))
    consumer.run()
}
