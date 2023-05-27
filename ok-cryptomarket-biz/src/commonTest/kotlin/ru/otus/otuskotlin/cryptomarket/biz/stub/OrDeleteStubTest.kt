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
class OrDeleteStubTest {

    private val processor = CpmkOrProcessor()
    val id = CpmkOrId("666")

    @Test
    fun delete() = runTest {

        val ctx = CpmkContext(
            command = CpmkCommand.DELETE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.SUCCESS,
            orRequest = CpmkOr(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = CpmkOrStub.get()
        assertEquals(stub.id, ctx.orResponse.id)
        assertEquals(stub.accountNumber, ctx.orResponse.accountNumber)
        assertEquals(stub.walletNumber, ctx.orResponse.walletNumber)
        assertEquals(stub.fiatCurrency, ctx.orResponse.fiatCurrency)
        assertEquals(stub.cryptoCurrency, ctx.orResponse.cryptoCurrency)
        assertEquals(stub.action, ctx.orResponse.action)
        assertEquals(stub.value, ctx.orResponse.value)
    }

    @Test
    fun badId() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.DELETE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.BAD_ID,
            orRequest = CpmkOr(),
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CpmkContext(
            command = CpmkCommand.DELETE,
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
            command = CpmkCommand.DELETE,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.STUB,
            stubCase = CpmkStubs.BAD_WALLET,
            orRequest = CpmkOr(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkOr(), ctx.orResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
