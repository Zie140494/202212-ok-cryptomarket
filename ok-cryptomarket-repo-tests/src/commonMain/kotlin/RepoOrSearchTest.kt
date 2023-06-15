package ru.otus.otuskotlin.cryptomarket.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkAction
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOr
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkUserId
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrFilterRequest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoOrSearchTest {
    abstract val repo: IOrRepository

    protected open val initializedObjects: List<CpmkOr> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchOr(DbOrFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchAction() = runRepoTest {
        val result = repo.searchOr(DbOrFilterRequest(action = CpmkAction.BUY))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitOrs("search") {

        val searchOwnerId = CpmkUserId("owner-124")
        override val initObjects: List<CpmkOr> = listOf(
            createInitTestModel("ad1", action = CpmkAction.NONE),
            createInitTestModel("ad2", ownerId = searchOwnerId, action = CpmkAction.NONE),
            createInitTestModel("ad3", action = CpmkAction.BUY),
            createInitTestModel("ad4", ownerId = searchOwnerId, action = CpmkAction.NONE),
            createInitTestModel("ad5", action = CpmkAction.BUY),
        )
    }
}
