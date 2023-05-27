package ru.otus.otuskotlin.cryptomarket.biz.groups

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.chain

fun ICorChainDsl<CpmkContext>.operation(walletNumber: String, command: CpmkCommand, block: ICorChainDsl<CpmkContext>.() -> Unit) = chain {
    block()
    this.walletNumber = walletNumber
    on { this.command == command && state == CpmkState.RUNNING }
}
