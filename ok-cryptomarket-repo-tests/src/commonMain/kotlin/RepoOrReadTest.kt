package ru.otus.otuskotlin.cryptomarket.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOr
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrId
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrIdRequest
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoOrReadTest {
    abstract val repo: IOrRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readOr(DbOrIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readOr(DbOrIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitOrs("delete") {
        override val initObjects: List<CpmkOr> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = CpmkOrId("or-repo-read-notFound")

    }
}
