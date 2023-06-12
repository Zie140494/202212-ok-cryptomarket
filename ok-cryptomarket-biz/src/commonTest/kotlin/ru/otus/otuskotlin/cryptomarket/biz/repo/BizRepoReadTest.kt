package ru.otus.otuskotlin.cryptomarket.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import repo.repoNotFoundTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.OrRepositoryMock
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest {

    private val userId = CpmkUserId("321")
    private val command = CpmkCommand.READ
    private val initOr = CpmkOr(
        id = CpmkOrId("123"),
        walletNumber = "abc",
        accountNumber = "abc",
        ownerId = userId,
        fiatCurrency = CpmkFiatCurrency.RUB,
        cryptoCurrency = CpmkCryptoCurrency.BTC,
        action = CpmkAction.BUY,
    )
    private val repo by lazy { OrRepositoryMock(
        invokeReadOr = {
            DbOrResponse(
                isSuccess = true,
                data = initOr,
            )
        }
    ) }
    private val settings by lazy {
        CpmkCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { CpmkOrProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = CpmkContext(
            command = command,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.TEST,
            orRequest = CpmkOr(
                id = CpmkOrId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkState.FINISHING, ctx.state)
        assertEquals(initOr.id, ctx.orResponse.id)
        assertEquals(initOr.walletNumber, ctx.orResponse.walletNumber)
        assertEquals(initOr.accountNumber, ctx.orResponse.accountNumber)
        assertEquals(initOr.fiatCurrency, ctx.orResponse.fiatCurrency)
        assertEquals(initOr.cryptoCurrency, ctx.orResponse.cryptoCurrency)
        assertEquals(initOr.action, ctx.orResponse.action)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
