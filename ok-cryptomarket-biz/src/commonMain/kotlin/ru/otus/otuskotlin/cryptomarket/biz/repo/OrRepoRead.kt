package ru.otus.otuskotlin.cryptomarket.biz.repo

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrIdRequest
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение заявки из БД"
    on { state == CpmkState.RUNNING }
    handle {
        val request = DbOrIdRequest(orValidated)
        val result = orRepo.readOr(request)
        val resultOr = result.data
        if (result.isSuccess && resultOr != null) {
            orRepoRead = resultOr
        } else {
            state = CpmkState.FAILING
            errors.addAll(result.errors)
        }
    }
}
