package ru.otus.otuskotlin.cryptomarket.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OrCreateStubTest {

    private val processor = CpmkOrProcessor()
    val id = CpmkOrId("666")
    val accountNumber = "12345678901234567890"
    val walletNumber = "x123456"
    val fiatCurrency = CpmkFiatCurrency.RUB
    val cryptoCurrency = CpmkCryptoCurrency.BTC
    val action = CpmkAction.BUY
    val value = 0F

    @Test
    fun create() = runTest {

        val ctx = CpmkContext(
            command = CpmkCommand.CREATE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.SUCCESS,
            orRequest = CpmkOr(
                id = id,
                accountNumber = accountNumber,
                walletNumber = walletNumber,
                fiatCurrency = fiatCurrency,
                cryptoCurrency = cryptoCurrency,
                action = action,
                value = value
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkOrStub.get().id, ctx.orResponse.id)
        assertEquals(accountNumber, ctx.orResponse.accountNumber)
        assertEquals(walletNumber, ctx.orResponse.walletNumber)
        assertEquals(fiatCurrency, ctx.orResponse.fiatCurrency)
        assertEquals(cryptoCurrency, ctx.orResponse.cryptoCurrency)
        assertEquals(action, ctx.orResponse.action)
        assertEquals(value, ctx.orResponse.value)
    }

    @Test
    fun badAccountNumber() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.CREATE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.BAD_ACCOUNT_NUMBER,
            orRequest = CpmkOr(
                id = id,
                accountNumber = accountNumber,
                walletNumber = walletNumber,
                fiatCurrency = fiatCurrency,
                cryptoCurrency = cryptoCurrency,
                action = action,
                value = value
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("accountNumber", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badWalletNumber() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.CREATE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.BAD_WALLET,
            orRequest = CpmkOr(
                id = id,
                accountNumber = accountNumber,
                walletNumber = walletNumber,
                fiatCurrency = fiatCurrency,
                cryptoCurrency = cryptoCurrency,
                action = action,
                value = value
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("walletNumber", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.CREATE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.DB_ERROR,
            orRequest = CpmkOr(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.CREATE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.BAD_ID,
            orRequest = CpmkOr(
                id = id,
                accountNumber = accountNumber,
                walletNumber = walletNumber,
                fiatCurrency = fiatCurrency,
                cryptoCurrency = cryptoCurrency,
                action = action,
                value = value
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
