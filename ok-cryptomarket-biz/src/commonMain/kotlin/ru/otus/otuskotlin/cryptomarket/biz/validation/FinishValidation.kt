package ru.otus.otuskotlin.cryptomarket.biz.validation

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == CpmkState.RUNNING }
    handle {
        orValidated = orValidating
    }
}

fun ICorChainDsl<CpmkContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == CpmkState.RUNNING }
    handle {
        orFilterValidated = orFilterValidating
    }
}
