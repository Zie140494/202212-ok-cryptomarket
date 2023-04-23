package ru.otus.otuskotlin.cryptomarket.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import ru.otus.otuskotlin.cryptomarket.api.models.IRequest
import ru.otus.otuskotlin.cryptomarket.api.models.IResponse

val apiMapper = ObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

fun apiRequestSerialize(request: IRequest): String = apiMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    apiMapper.readValue(json, IRequest::class.java) as T

fun apiResponseSerialize(response: IResponse): String = apiMapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiV1ResponseDeserialize(json: String): T =
    apiMapper.readValue(json, IResponse::class.java) as T
