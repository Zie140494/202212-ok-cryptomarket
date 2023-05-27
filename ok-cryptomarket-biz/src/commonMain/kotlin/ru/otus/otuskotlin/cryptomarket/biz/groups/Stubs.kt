package ru.otus.otuskotlin.cryptomarket.biz.groups

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkWorkMode
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.chain

fun ICorChainDsl<CpmkContext>.stubs(walletNumber: String, block: ICorChainDsl<CpmkContext>.() -> Unit) = chain {
    block()
    this.walletNumber = walletNumber
    on { workMode == CpmkWorkMode.STUB && state == CpmkState.RUNNING }
}
