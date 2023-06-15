package ru.otus.otuskotlin.cryptomarket.biz.repo

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrIdRequest
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление заявки из БД по ID"
    on { state == CpmkState.RUNNING }
    handle {
        val request = DbOrIdRequest(orRepoPrepare)
        val result = orRepo.deleteOr(request)
        if (!result.isSuccess) {
            state = CpmkState.FAILING
            errors.addAll(result.errors)
        }
        orRepoDone = orRepoRead
    }
}
