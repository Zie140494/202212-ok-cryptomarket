package ru.otus.otuskotlin.cryptomarket.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrRequest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoOrUpdateTest {
    abstract val repo: IOrRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = CpmkOrId("or-repo-update-not-found")
    protected val lockBad = CpmkOrLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = CpmkOrLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        CpmkOr(
            id = updateSucc.id,
            accountNumber = "update object accountNumber",
            walletNumber = "update object walletNumber",
            ownerId = CpmkUserId("owner-123"),
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = CpmkOr(
        id = updateIdNotFound,
        accountNumber = "update object not found accountNumber",
        walletNumber = "update object not found walletNumber",
        ownerId = CpmkUserId("owner-123"),
        fiatCurrency = CpmkFiatCurrency.RUB,
        cryptoCurrency = CpmkCryptoCurrency.BTC,
        action = CpmkAction.BUY,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        CpmkOr(
            id = updateConc.id,
            accountNumber = "update object not found accountNumber",
            walletNumber = "update object not found walletNumber",
            ownerId = CpmkUserId("owner-123"),
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateOr(DbOrRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.accountNumber, result.data?.accountNumber)
        assertEquals(reqUpdateSucc.walletNumber, result.data?.walletNumber)
        assertEquals(reqUpdateSucc.action, result.data?.action)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateOr(DbOrRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateOr(DbOrRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitOrs("update") {
        override val initObjects: List<CpmkOr> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
