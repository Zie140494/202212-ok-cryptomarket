package ru.otus.otuskotlin.cryptomarket.biz.workers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs

fun ICorChainDsl<CpmkContext>.stubDbError(title: String) = worker {
    this.title = title
    on { this.stubCase == CpmkStubs.DB_ERROR && this.state == CpmkState.RUNNING }
    handle {
        state = CpmkState.FAILING
        this.errors.add(
            CpmkError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
