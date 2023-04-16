package ru.otus.otuskotlin.cryptomarket.api

import ru.otus.otuskotlin.cryptomarket.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = OrCreateResponse(
        responseType = "create",
        requestId = "123",
        or = OrResponseObject(
            accountNumber = "01234567890123456789",
            walletNumber = "x0123",
            fiatCurrency = FiatCurrency.RUB,
            cryptoCurrency = CryptoCurrency.BTC,
            action = Action.BUY
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(response)

        assertContains(json, Regex("\"accountNumber\":\\s*\"01234567890123456789\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(response)
        val obj = apiMapper.readValue(json, IResponse::class.java) as OrCreateResponse

        assertEquals(response, obj)
    }
}
