package ru.otus.otuskotlin.cryptomarket.biz.general

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkWorkMode
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != CpmkWorkMode.STUB }
    handle {
        orResponse = orRepoDone
        orsResponse = orsRepoDone
        state = when (val st = state) {
            CpmkState.RUNNING -> CpmkState.FINISHING
            else -> st
        }
    }
}
