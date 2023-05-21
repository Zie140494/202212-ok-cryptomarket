package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorValidation
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail

fun ICorChainDsl<CpmkContext>.validateWalletNumberNotEmpty(walletNumber: String) = worker {
    this.walletNumber = walletNumber
    on { orValidating.walletNumber.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "walletNumber",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
