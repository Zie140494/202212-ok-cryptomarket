package ru.otus.otuskotlin.cryptomarket.app.kafka

import ru.otus.otuskotlin.cryptomarket.api.apiRequestDeserialize
import ru.otus.otuskotlin.cryptomarket.api.models.IRequest
import ru.otus.otuskotlin.cryptomarket.api.models.IResponse
import ru.otus.otuskotlin.cryptomarket.api.apiResponseSerialize
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.mappers.fromTransport
import ru.otus.otuskotlin.cryptomarket.mappers.toTransportOr

class ConsumerStrategyCpmk : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicIn, config.kafkaTopicOut)
    }

    override fun serialize(source: CpmkContext): String {
        val response: IResponse = source.toTransportOr()
        return apiResponseSerialize(response)
    }

    override fun deserialize(value: String, target: CpmkContext) {
        val request: IRequest = apiRequestDeserialize(value)
        target.fromTransport(request)
    }
}