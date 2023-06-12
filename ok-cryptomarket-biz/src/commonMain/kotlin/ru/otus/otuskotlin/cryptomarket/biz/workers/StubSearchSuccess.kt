package ru.otus.otuskotlin.cryptomarket.biz.workers

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStub

fun ICorChainDsl<CpmkContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CpmkStubs.SUCCESS && state == CpmkState.RUNNING }
    handle {
        state = CpmkState.FINISHING
        orsResponse.addAll(CpmkOrStub.prepareSearchList(orFilterRequest.searchString, CpmkAction.BUY))
    }
}
