package ru.otus.otuskotlin.cryptomarket.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.cryptomarket.backend.repo.tests.OrRepositoryMock
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.CpmkCorSettings
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = CpmkUserId("321")
    private val command = CpmkCommand.SEARCH
    private val initAd = CpmkOr(
        id = CpmkOrId("123"),
        walletNumber = "abc",
        accountNumber = "abc",
        ownerId = userId,
        fiatCurrency = CpmkFiatCurrency.RUB,
        cryptoCurrency = CpmkCryptoCurrency.BTC,
        action = CpmkAction.BUY,
    )
    private val repo by lazy { OrRepositoryMock(
        invokeSearchOr = {
            DbOrsResponse(
                isSuccess = true,
                data = listOf(initAd),
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
    fun repoSearchSuccessTest() = runTest {
        val ctx = CpmkContext(
            command = command,
            state = CpmkState.NONE,
            workMode = CpmkWorkMode.TEST,
            orFilterRequest = CpmkOrFilter(
                searchString = "ab",
                action = CpmkAction.BUY
            ),
        )
        processor.exec(ctx)
        assertEquals(CpmkState.FINISHING, ctx.state)
        assertEquals(1, ctx.orsResponse.size)
    }
}
