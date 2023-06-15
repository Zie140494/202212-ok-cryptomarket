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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = CpmkUserId("321")
    private val command = CpmkCommand.UPDATE
    private val initAd = CpmkOr(
        id = CpmkOrId("123"),
        walletNumber = "abc",
        accountNumber = "12345678901234567890",
        ownerId = userId,
        fiatCurrency = CpmkFiatCurrency.RUB,
        cryptoCurrency = CpmkCryptoCurrency.BTC,
        action = CpmkAction.BUY,
    )
    private val repo by lazy { OrRepositoryMock(
        invokeReadOr = {
            DbOrResponse(
                isSuccess = true,
                data = initAd,
            )
        },
        invokeUpdateOr = {
            DbOrResponse(
                isSuccess = true,
                data = CpmkOr(
                    id = CpmkOrId("123"),
                    walletNumber = "abc",
                    accountNumber = "12345678901234567890",
                    fiatCurrency = CpmkFiatCurrency.RUB,
                    cryptoCurrency = CpmkCryptoCurrency.BTC,
                    action = CpmkAction.BUY,
                )
            )
        }
    ) }
    private val settings by lazy {
        CpmkCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { CpmkOrProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = CpmkOr(
            id = CpmkOrId("123"),
            walletNumber = "abc",
            accountNumber = "12345678901234567890",
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = CpmkOrLock("123-234-abc-ABC"),
        )
        val ctx = CpmkContext(
            command = command,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.TEST,
            orRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(CpmkState.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.orResponse.id)
        assertEquals(adToUpdate.walletNumber, ctx.orResponse.walletNumber)
        assertEquals(adToUpdate.accountNumber, ctx.orResponse.accountNumber)
        assertEquals(adToUpdate.fiatCurrency, ctx.orResponse.fiatCurrency)
        assertEquals(adToUpdate.cryptoCurrency, ctx.orResponse.cryptoCurrency)
        assertEquals(adToUpdate.action, ctx.orResponse.action)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
