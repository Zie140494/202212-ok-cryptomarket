package ru.otus.otuskotlin.cryptomarket.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = CpmkOrStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationWalletNumberCorrect(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = stub.id,
            walletNumber = "x0123",
            accountNumber = "1234567890",
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            fiatCurrency = CpmkFiatCurrency.RUB,
            action = CpmkAction.BUY,
            value = 0F
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CpmkState.FAILING, ctx.state)
    assertEquals("x0123", ctx.orValidated.walletNumber)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationWalletNumberTrim(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = stub.id,
            walletNumber = "x0123",
            accountNumber = "1234567890",
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            fiatCurrency = CpmkFiatCurrency.RUB,
            action = CpmkAction.BUY,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CpmkState.FAILING, ctx.state)
    assertEquals("x0123", ctx.orValidated.walletNumber)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationWalletNumberEmpty(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = stub.id,
            walletNumber = "",
            accountNumber = "1234567890",
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            fiatCurrency = CpmkFiatCurrency.RUB,
            action = CpmkAction.BUY,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CpmkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("walletNumber", error?.field)
    assertContains(error?.message ?: "", "walletNumber")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationWalletNumberSymbols(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = CpmkOrId("123"),
            walletNumber = "0123",
            accountNumber = "1234567890",
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            fiatCurrency = CpmkFiatCurrency.RUB,
            action = CpmkAction.BUY,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CpmkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("walletNumber", error?.field)
    assertContains(error?.message ?: "", "walletNumber")
}
