package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorValidation
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail

fun ICorChainDsl<CpmkContext>.validateAccountNumberNotEmpty(title: String) = worker {
    this.title = title
    on { orValidating.accountNumber.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "accountNumber",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
