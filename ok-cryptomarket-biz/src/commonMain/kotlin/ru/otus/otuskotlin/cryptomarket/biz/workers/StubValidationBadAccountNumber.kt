package ru.otus.otuskotlin.cryptomarket.biz.workers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs

fun ICorChainDsl<CpmkContext>.stubValidationBadAccountNumber(accountNumber: String) = worker {
    this.accountNumber = accountNumber
    on { stubCase == CpmkStubs.BAD_ACCOUNT_NUMBER && state == CpmkState.RUNNING }
    handle {
        state = CpmkState.FAILING
        this.errors.add(
            CpmkError(
                group = "validation",
                code = "validation-accountNumber",
                field = "accountNumber",
                message = "Wrong accountNumber field"
            )
        )
    }
}
