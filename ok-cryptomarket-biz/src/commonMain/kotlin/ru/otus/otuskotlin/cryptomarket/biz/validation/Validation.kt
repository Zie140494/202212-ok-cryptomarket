package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.chain

fun ICorChainDsl<CpmkContext>.validation(block: ICorChainDsl<CpmkContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == CpmkState.RUNNING }
}
