package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.helpers.errorValidation
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { orValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
