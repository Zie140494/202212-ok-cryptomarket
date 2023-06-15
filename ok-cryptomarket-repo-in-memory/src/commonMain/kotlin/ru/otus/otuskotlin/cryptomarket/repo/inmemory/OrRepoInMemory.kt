package ru.otus.otuskotlin.cryptomarket.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.cryptomarket.backend.repository.inmemory.model.OrEntity
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class OrRepoInMemory(
    initObjects: List<CpmkOr> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IOrRepository {

    private val cache = Cache.Builder<String, OrEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(or: CpmkOr) {
        val entity = OrEntity(or)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createOr(rq: DbOrRequest): DbOrResponse {
        val key = randomUuid()
        val or = rq.or.copy(id = CpmkOrId(key), lock = CpmkOrLock(randomUuid()))
        val entity = OrEntity(or)
        cache.put(key, entity)
        return DbOrResponse(
            data = or,
            isSuccess = true,
        )
    }

    override suspend fun readOr(rq: DbOrIdRequest): DbOrResponse {
        val key = rq.id.takeIf { it != CpmkOrId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbOrResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    private suspend fun doUpdate(key: String, oldLock: String, okBlock: (oldOr: OrEntity) -> DbOrResponse): DbOrResponse = mutex.withLock {
        val oldOr = cache.get(key)
        when {
            oldOr == null -> resultErrorNotFound
            oldOr.lock != oldLock -> DbOrResponse(
                data = oldOr.toInternal(),
                isSuccess = false,
                errors = listOf(errorRepoConcurrency(CpmkOrLock(oldLock), oldOr.lock?.let { CpmkOrLock(it) }))
            )

            else -> okBlock(oldOr)
        }
    }

    override suspend fun updateOr(rq: DbOrRequest): DbOrResponse {
        val key = rq.or.id.takeIf { it != CpmkOrId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.or.lock.takeIf { it != CpmkOrLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newOr = rq.or.copy()
        val entity = OrEntity(newOr)
        return doUpdate(key, oldLock) {
            cache.put(key, entity)
            DbOrResponse(
                data = newOr,
                isSuccess = true,
            )
        }
    }

    override suspend fun deleteOr(rq: DbOrIdRequest): DbOrResponse {
        val key = rq.id.takeIf { it != CpmkOrId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != CpmkOrLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return doUpdate(key, oldLock) {oldOr ->
            cache.invalidate(key)
            DbOrResponse(
                data = oldOr.toInternal(),
                isSuccess = true,
            )
        }
    }

    override suspend fun searchOr(rq: DbOrFilterRequest): DbOrsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != CpmkUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.action.takeIf { it != CpmkAction.NONE }?.let {
                    it.name == entry.value.action
                } ?: true
            }
            .filter { entry ->
                rq.walletNumberFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.walletNumber?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbOrsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbOrResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CpmkError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbOrResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CpmkError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbOrResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                CpmkError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
