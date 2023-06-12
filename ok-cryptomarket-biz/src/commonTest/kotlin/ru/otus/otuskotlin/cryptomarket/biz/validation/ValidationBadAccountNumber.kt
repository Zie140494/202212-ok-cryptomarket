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
fun validationAccountNumberCorrect(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = stub.id,
            walletNumber = "abc",
            accountNumber = "abc",
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = CpmkOrLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CpmkState.FAILING, ctx.state)
    assertEquals("abc", ctx.orValidated.accountNumber)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAccountNumberTrim(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = stub.id,
            walletNumber = "abc",
            accountNumber = " \n\tabc \n\t",
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = CpmkOrLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CpmkState.FAILING, ctx.state)
    assertEquals("abc", ctx.orValidated.accountNumber)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAccountNumberEmpty(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = stub.id,
            walletNumber = "abc",
            accountNumber = "",
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = CpmkOrLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CpmkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationAccountNumberSymbols(command: CpmkCommand, processor: CpmkOrProcessor) = runTest {
    val ctx = CpmkContext(
        command = command,
        state = CpmkState.NONE,
        workMode = CpmkWorkMode.TEST,
        orRequest = CpmkOr(
            id = stub.id,
            walletNumber = "abc",
            accountNumber = "!@#$%^&*(),.{}",
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = CpmkOrLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CpmkState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
