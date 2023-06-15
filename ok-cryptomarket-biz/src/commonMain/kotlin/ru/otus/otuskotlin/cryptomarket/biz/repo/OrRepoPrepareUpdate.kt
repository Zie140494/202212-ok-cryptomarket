package ru.otus.otuskotlin.cryptomarket.biz.repo

import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker

fun ICorChainDsl<CpmkContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == CpmkState.RUNNING }
    handle {
        orRepoPrepare = orRepoRead.deepCopy().apply {
            this.walletNumber = orValidated.walletNumber
            accountNumber = orValidated.accountNumber
            fiatCurrency = orValidated.fiatCurrency
            cryptoCurrency = orValidated.cryptoCurrency
            action = orValidated.action
        }
    }
}
