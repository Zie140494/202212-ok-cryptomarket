package ru.otus.otuskotlin.cryptomarket.biz.general

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkWorkMode
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.chain

fun ICorChainDsl<CpmkContext>.stubs(title: String, block: ICorChainDsl<CpmkContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == CpmkWorkMode.STUB && state == CpmkState.RUNNING }
}
