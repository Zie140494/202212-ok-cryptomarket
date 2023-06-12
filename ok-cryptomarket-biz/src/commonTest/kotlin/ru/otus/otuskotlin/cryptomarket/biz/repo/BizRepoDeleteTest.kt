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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = CpmkUserId("321")
    private val command = CpmkCommand.DELETE
    private val initOr = CpmkOr(
        id = CpmkOrId("123"),
        walletNumber = "abc",
        accountNumber = "abc",
        ownerId = userId,
        fiatCurrency = CpmkFiatCurrency.RUB,
        cryptoCurrency = CpmkCryptoCurrency.BTC,
        action = CpmkAction.BUY,
        lock = CpmkOrLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        OrRepositoryMock(
            invokeReadOr = {
               DbOrResponse(
                   isSuccess = true,
                   data = initOr,
               )
            },
            invokeDeleteOr = {
                if (it.id == initOr.id)
                    DbOrResponse(
                        isSuccess = true,
                        data = initOr
                    )
                else DbOrResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        CpmkCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { CpmkOrProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val orToUpdate = CpmkOr(
            id = CpmkOrId("123"),
            lock = CpmkOrLock("123-234-abc-ABC"),
        )
        val ctx = CpmkContext(
            command = command,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.TEST,
            orRequest = orToUpdate,
        )
        processor.exec(ctx)
        assertEquals(CpmkState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initOr.id, ctx.orResponse.id)
        assertEquals(initOr.walletNumber, ctx.orResponse.walletNumber)
        assertEquals(initOr.accountNumber, ctx.orResponse.accountNumber)
        assertEquals(initOr.fiatCurrency, ctx.orResponse.fiatCurrency)
        assertEquals(initOr.cryptoCurrency, ctx.orResponse.cryptoCurrency)
        assertEquals(initOr.action, ctx.orResponse.action)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
