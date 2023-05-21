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
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class OrSearchStubTest {

    private val processor = CpmkOrProcessor()
    val filter = CpmkOrFilter(searchString = "crypto")

    @Test
    fun read() = runTest {

        val ctx = CpmkContext(
            command = CpmkCommand.SEARCH,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.SUCCESS,
            orFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.orsResponse.size > 1)
        val first = ctx.orsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.walletNumber.contains(filter.searchString))
        assertTrue(first.accountNumber.contains(filter.searchString))
        with (CpmkOrStub.get()) {
            assertEquals(accountNumber, first.accountNumber)
            assertEquals(walletNumber, first.walletNumber)
            assertEquals(fiatCurrency, first.fiatCurrency)
            assertEquals(cryptoCurrency, first.cryptoCurrency)
            assertEquals(action, first.action)
            assertEquals(value, first.value)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.SEARCH,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.BAD_ID,
            orFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.SEARCH,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.DB_ERROR,
            orFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.SEARCH,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.BAD_WALLET,
            orFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
