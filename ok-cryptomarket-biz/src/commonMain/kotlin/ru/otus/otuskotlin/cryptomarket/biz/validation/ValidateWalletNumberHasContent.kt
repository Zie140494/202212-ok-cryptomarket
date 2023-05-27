package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.helpers.errorValidation
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail

fun ICorChainDsl<CpmkContext>.validateWalletNumberHasContent(walletNumber: String) = worker {
    this.walletNumber = walletNumber
    val regExp = Regex("\\p{L}")
    on { orValidating.walletNumber.isNotEmpty() && ! orValidating.walletNumber.contains(regExp) }
    handle {
        fail(
            errorValidation(
            field = "walletNumber",
            violationCode = "noContent",
            description = "field must contain leters"
        )
        )
    }
}
