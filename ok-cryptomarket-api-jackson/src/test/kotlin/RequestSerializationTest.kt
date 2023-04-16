package ru.otus.otuskotlin.cryptomarket.api

import ru.otus.otuskotlin.cryptomarket.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = OrCreateRequest(
        requestType = "create",
        requestId = "123",
        debug = OrDebug(
            mode = OrRequestDebugMode.STUB,
            stub = OrRequestDebugStubs.BAD_WALLET
        ),
        or = OrCreateObject(
            accountNumber = "01234567890123456789",
            walletNumber = "x0123",
            fiatCurrency = FiatCurrency.RUB,
            cryptoCurrency = CryptoCurrency.BTC,
            action = Action.BUY
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)

        assertContains(json, Regex("\"accountNumber\":\\s*\"01234567890123456789\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badWallet\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, IRequest::class.java) as OrCreateRequest

        assertEquals(request, obj)
    }
}
