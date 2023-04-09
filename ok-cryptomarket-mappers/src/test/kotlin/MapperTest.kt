package ru.otus.otuskotlin.cryptomarket.mappers

import org.junit.Test
import ru.otus.otuskotlin.cryptomarket.api.models.*
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs

import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = OrCreateRequest(
            requestId = "1234",
            debug = OrDebug(
                mode = OrRequestDebugMode.STUB,
                stub = OrRequestDebugStubs.SUCCESS,
            ),
            or = OrCreateObject(
                accountNumber = "01234567890123456789",
                walletNumber = "x0123",
                fiatCurrency = FiatCurrency.RUB,
                cryptoCurrency = CryptoCurrency.BTC,
                action = Action.BUY
            ),
        )

        val context = CpmkContext()
        context.fromTransport(req)

        assertEquals(CpmkStubs.SUCCESS, context.stubCase)
        assertEquals(CpmkWorkMode.STUB, context.workMode)
        assertEquals("01234567890123456789", context.orRequest.accountNumber)
        assertEquals("x0123", context.orRequest.walletNumber)
        assertEquals(CpmkFiatCurrency.RUB, context.orRequest.fiatCurrency)
        assertEquals(CpmkCryptoCurrency.BTC, context.orRequest.cryptoCurrency)
        assertEquals(CpmkAction.BUY, context.orRequest.action)
    }

    @Test
    fun toTransport() {
        val context = CpmkContext(
            requestId = CpmkRequestId("1234"),
            command = CpmkCommand.CREATE,
            orResponse = CpmkOr(
                accountNumber = "01234567890123456789",
                walletNumber = "x0123",
                fiatCurrency = CpmkFiatCurrency.RUB,
                cryptoCurrency = CpmkCryptoCurrency.BTC,
                action = CpmkAction.BUY
            ),
            errors = mutableListOf(
                CpmkError(
                    code = "err",
                    group = "request",
                    field = "walletNumber",
                    message = "wrong walletNumber",
                )
            ),
            state = CpmkState.RUNNING,
        )

        val req = context.toTransportOr() as OrCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("01234567890123456789", req.or?.accountNumber)
        assertEquals("x0123", req.or?.walletNumber)
        assertEquals(FiatCurrency.RUB, req.or?.fiatCurrency)
        assertEquals(CryptoCurrency.BTC, req.or?.cryptoCurrency)
        assertEquals(Action.BUY, req.or?.action)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("walletNumber", req.errors?.firstOrNull()?.field)
        assertEquals("wrong walletNumber", req.errors?.firstOrNull()?.message)
    }
}
