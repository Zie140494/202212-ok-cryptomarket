package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorValidation
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkOrLock
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в CpmkOrId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { orValidating.lock != CpmkOrLock.NONE && !orValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = orValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
