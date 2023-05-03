package ru.otus.otuskotlin.cryptomarket.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyCpmk(),))
    consumer.run()
}
