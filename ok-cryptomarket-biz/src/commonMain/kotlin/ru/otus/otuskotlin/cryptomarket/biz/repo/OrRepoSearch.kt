package ru.otus.otuskotlin.cryptomarket.biz.repo

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.common.repo.DbOrFilterRequest
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == CpmkState.RUNNING }
    handle {
        val request = DbOrFilterRequest(
            walletNumberFilter = orFilterValidated.searchString,
            ownerId = orFilterValidated.ownerId,
            action = orFilterValidated.action,
        )
        val result = orRepo.searchOr(request)
        val resultOrs = result.data
        if (result.isSuccess && resultOrs != null) {
            orsRepoDone = resultOrs.toMutableList()
        } else {
            state = CpmkState.FAILING
            errors.addAll(result.errors)
        }
    }
}
