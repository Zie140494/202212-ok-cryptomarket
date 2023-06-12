package ru.otus.otuskotlin.cryptomarket.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.OrRepositoryMock
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = CpmkUserId("321")
    private val command = CpmkCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = OrRepositoryMock(
        invokeCreateOr = {
            DbOrResponse(
                isSuccess = true,
                data = CpmkOr(
                    id = CpmkOrId(uuid),
                    walletNumber = it.or.walletNumber,
                    accountNumber = it.or.accountNumber,
                    value = it.or.value,
                    ownerId = userId,
                    fiatCurrency = it.or.fiatCurrency,
                    cryptoCurrency = it.or.cryptoCurrency,
                    action = it.or.action,
                )
            )
        }
    )
    private val settings = CpmkCorSettings(
        repoTest = repo
    )
    private val processor = CpmkOrProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = CpmkContext(
            command = command,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.TEST,
            orRequest = CpmkOr(
                walletNumber = "abc",
                accountNumber = "abc",
                fiatCurrency = CpmkFiatCurrency.RUB,
                cryptoCurrency = CpmkCryptoCurrency.BTC,
                action = CpmkAction.BUY,
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkState.FINISHING, ctx.state)
        assertNotEquals(CpmkOrId.NONE, ctx.orResponse.id)
        assertEquals("abc", ctx.orResponse.walletNumber)
        assertEquals("abc", ctx.orResponse.accountNumber)
        assertEquals(CpmkFiatCurrency.RUB, ctx.orResponse.fiatCurrency)
        assertEquals(CpmkCryptoCurrency.BTC, ctx.orResponse.cryptoCurrency)
        assertEquals(CpmkAction.BUY, ctx.orResponse.action)
    }
}
