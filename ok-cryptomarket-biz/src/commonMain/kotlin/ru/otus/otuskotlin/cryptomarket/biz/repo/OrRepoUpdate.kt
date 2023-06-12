package ru.otus.otuskotlin.cryptomarket.biz.repo

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrRequest
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == CpmkState.RUNNING }
    handle {
        val request = DbOrRequest(orRepoPrepare)
        val result = orRepo.updateOr(request)
        val resultOr = result.data
        if (result.isSuccess && resultOr != null) {
            orRepoDone = resultOr
        } else {
            state = CpmkState.FAILING
            errors.addAll(result.errors)
            orRepoDone
        }
    }
}
