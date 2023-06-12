package ru.otus.otuskotlin.cryptomarket.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.otus.otuskotlin.cryptomarket.common.helpers.asCpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrFilterRequest
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrIdRequest
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrRequest
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrResponse
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrsResponse
import ru.otus.otuskotlin.cryptomarket.common.repo.IOrRepository

class RepoOrSQL(
    properties: SqlProperties,
    initObjects: Collection<CpmkOr> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IOrRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(OrTable)
            SchemaUtils.create(OrTable)
            initObjects.forEach { createOr(it) }
        }
    }

    private fun createOr(or: CpmkOr): CpmkOr {
        val res = OrTable.insert {
            to(it, or, randomUuid)
        }

        return OrTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbOrResponse): DbOrResponse =
        transactionWrapper(block) { DbOrResponse.error(it.asCpmkError()) }

    override suspend fun createOr(rq: DbOrRequest): DbOrResponse = transactionWrapper {
        DbOrResponse.success(createOr(rq.or))
    }

    private fun read(id: CpmkOrId): DbOrResponse {
        val res = OrTable.select {
            OrTable.id eq id.asString()
        }.singleOrNull() ?: return DbOrResponse.errorNotFound
        return DbOrResponse.success(OrTable.from(res))
    }

    override suspend fun readOr(rq: DbOrIdRequest): DbOrResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: CpmkOrId,
        lock: CpmkOrLock,
        block: (CpmkOr) -> DbOrResponse
    ): DbOrResponse =
        transactionWrapper {
            if (id == CpmkOrId.NONE) return@transactionWrapper DbOrResponse.errorEmptyId

            val current = OrTable.select { OrTable.id eq id.asString() }
                .firstOrNull()
                ?.let { OrTable.from(it) }

            when {
                current == null -> DbOrResponse.errorNotFound
                current.lock != lock -> DbOrResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateOr(rq: DbOrRequest): DbOrResponse = update(rq.or.id, rq.or.lock) {
        OrTable.update({ OrTable.id eq rq.or.id.asString() }) {
            to(it, rq.or.copy(lock = CpmkOrLock(randomUuid())), randomUuid)
        }
        read(rq.or.id)
    }

    override suspend fun deleteOr(rq: DbOrIdRequest): DbOrResponse = update(rq.id, rq.lock) {
        OrTable.deleteWhere { id eq rq.id.asString() }
        DbOrResponse.success(it)
    }

    override suspend fun searchOr(rq: DbOrFilterRequest): DbOrsResponse =
        transactionWrapper({
            val res = OrTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != CpmkUserId.NONE) {
                        add(OrTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.action != CpmkAction.NONE) {
                        add(OrTable.action eq rq.action)
                    }
                    if (rq.walletNumberFilter.isNotBlank()) {
                        add(
                            (OrTable.walletNumber like "%${rq.walletNumberFilter}%")
                                    or (OrTable.accountNumber like "%${rq.walletNumberFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbOrsResponse(data = res.map { OrTable.from(it) }, isSuccess = true)
        }, {
            DbOrsResponse.error(it.asCpmkError())
        })
}
