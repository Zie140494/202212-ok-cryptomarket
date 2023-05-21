package ru.otus.otuskotlin.cryptomarket.biz.workers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.initStatus(walletNumber: String) = worker() {
    this.walletNumber = walletNumber
    on { state == CpmkState.NONE }
    handle { state = CpmkState.RUNNING }
}
