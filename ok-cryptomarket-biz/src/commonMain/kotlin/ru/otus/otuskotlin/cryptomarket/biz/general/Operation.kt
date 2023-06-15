package ru.otus.otuskotlin.cryptomarket.biz.general

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.chain

fun ICorChainDsl<CpmkContext>.operation(title: String, command: CpmkCommand, block: ICorChainDsl<CpmkContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == CpmkState.RUNNING }
}
