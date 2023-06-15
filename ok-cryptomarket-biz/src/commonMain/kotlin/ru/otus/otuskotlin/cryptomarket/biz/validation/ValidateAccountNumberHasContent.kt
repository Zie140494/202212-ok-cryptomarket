package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorValidation
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.validateAccountNumberHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{Digit}")
    on { orValidating.accountNumber.isNotEmpty() && ! orValidating.accountNumber.contains(regExp) }
    handle {
        fail(
            errorValidation(
            field = "accountNumber",
            violationCode = "noContent",
            description = "field must contain letters"
        )
        )
    }
}
