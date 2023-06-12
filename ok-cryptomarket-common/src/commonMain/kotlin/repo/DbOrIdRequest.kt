package ru.otus.otuskotlin.cryptomarket.common.repo

import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOr
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrId
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrLock

data class DbOrIdRequest(
    val id: CpmkOrId,
    val lock: CpmkOrLock = CpmkOrLock.NONE,
) {
    constructor(or: CpmkOr): this(or.id, or.lock)
}
