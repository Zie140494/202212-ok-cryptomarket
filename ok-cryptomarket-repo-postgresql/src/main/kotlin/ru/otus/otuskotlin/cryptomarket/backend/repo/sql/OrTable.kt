package ru.otus.otuskotlin.cryptomarket.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.cryptomarket.common.models.*

object OrTable : Table("or") {

    val id = varchar("id", 128)
    val accountNumber = varchar("accountNumber", 128)
    val walletNumber = varchar("walletNumber", 128)
    val owner = varchar("owner", 128)
    val fiatCurrency = enumeration("fiatCurrency", CpmkFiatCurrency::class)
    val cryptoCurrency = enumeration("cryptoCurrency", CpmkCryptoCurrency::class)
    val action = enumeration("action", CpmkAction::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res : InsertStatement<Number>) =CpmkOr(
        id = CpmkOrId(res[id].toString()),
        accountNumber = res[accountNumber],
        walletNumber = res[walletNumber],
        ownerId = CpmkUserId(res[owner].toString()),
        fiatCurrency = res[fiatCurrency],
        cryptoCurrency = res[cryptoCurrency],
        action = res[action],
        lock = CpmkOrLock(res[lock])
    )

    // копипаста, можно избавиться, сделав свой интерфейс и обертки над InsertStatement и ResultRow
    // но ради двух методов нет смысла
    fun from(res : ResultRow) = CpmkOr(
        id = CpmkOrId(res[id].toString()),
        accountNumber = res[accountNumber],
        walletNumber = res[walletNumber],
        ownerId = CpmkUserId(res[owner].toString()),
        fiatCurrency = res[fiatCurrency],
        cryptoCurrency = res[cryptoCurrency],
        action = res[action],
        lock = CpmkOrLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, or: CpmkOr, randomUuid: () -> String) {
        it[id] = or.id.takeIf { it != CpmkOrId.NONE }?.asString() ?: randomUuid()
        it[accountNumber] = or.accountNumber
        it[walletNumber] = or.walletNumber
        it[owner] = or.ownerId.asString()
        it[fiatCurrency] = or.fiatCurrency
        it[cryptoCurrency] = or.cryptoCurrency
        it[action] = or.action
        it[lock] = or.lock.takeIf { it != CpmkOrLock.NONE }?.asString() ?: randomUuid()
    }

}