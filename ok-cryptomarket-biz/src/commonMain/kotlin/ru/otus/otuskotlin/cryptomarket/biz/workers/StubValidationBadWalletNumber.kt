package ru.otus.otuskotlin.cryptomarket.biz.workers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs

fun ICorChainDsl<CpmkContext>.stubValidationBadWalletNumber(walletNumber: String) = worker {
    this.walletNumber = walletNumber
    on { stubCase == CpmkStubs.BAD_WALLET && state == CpmkState.RUNNING }
    handle {
        state = CpmkState.FAILING
        this.errors.add(
            CpmkError(
                group = "validation",
                code = "validation-walletNumber",
                field = "walletNumber",
                message = "Wrong walletNumber field"
            )
        )
    }
}
