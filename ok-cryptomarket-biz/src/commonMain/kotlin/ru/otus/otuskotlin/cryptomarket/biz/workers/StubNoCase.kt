package ru.otus.otuskotlin.cryptomarket.biz.workers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.helpers.fail
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState

fun ICorChainDsl<CpmkContext>.stubNoCase(walletNumber: String) = worker {
    this.walletNumber = walletNumber
    on { state == CpmkState.RUNNING }
    handle {
        fail(
            CpmkError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
