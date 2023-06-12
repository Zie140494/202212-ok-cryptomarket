package ru.otus.otuskotlin.cryptomarket.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrRequest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoOrCreateTest {
    abstract val repo: IOrRepository

    protected open val lockNew: CpmkOrLock = CpmkOrLock("20000000-0000-0000-0000-000000000002")

    private val createObj = CpmkOr(
        accountNumber = "create object accountNumber",
        walletNumber = "create object walletNumber",
        ownerId = CpmkUserId("owner-123"),
        fiatCurrency = CpmkFiatCurrency.RUB,
        cryptoCurrency = CpmkCryptoCurrency.BTC,
        action = CpmkAction.BUY,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createOr(DbOrRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: CpmkOrId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.accountNumber, result.data?.accountNumber)
        assertEquals(expected.walletNumber, result.data?.walletNumber)
        assertEquals(expected.fiatCurrency, result.data?.fiatCurrency)
        assertEquals(expected.cryptoCurrency, result.data?.cryptoCurrency)
        assertEquals(expected.action, result.data?.action)
        assertNotEquals(CpmkOrId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitOrs("create") {
        override val initObjects: List<CpmkOr> = emptyList()
    }
}
