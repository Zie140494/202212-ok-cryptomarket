package ru.otus.otuskotlin.cryptomarket.biz.workers

import ru.otus.otuskotlin.cryptomarket.cor.ICorChainDsl
import ru.otus.otuskotlin.cryptomarket.cor.worker
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStub

fun ICorChainDsl<CpmkContext>.stubUpdateSuccess(walletNumber: String) = worker {
    this.walletNumber = walletNumber
    on { stubCase == CpmkStubs.SUCCESS && state == CpmkState.RUNNING }
    handle {
        state = CpmkState.FINISHING
        val stub = CpmkOrStub.prepareResult {
            orRequest.id.takeIf { it != CpmkOrId.NONE }?.also { this.id = it }
            orRequest.walletNumber.takeIf { it.isNotBlank() }?.also { this.walletNumber = it }
            orRequest.accountNumber.takeIf { it.isNotBlank() }?.also { this.accountNumber = it }
            orRequest.value.takeIf { it == 0F }?.also { this.value = it }
            orRequest.fiatCurrency.takeIf { it != CpmkFiatCurrency.NONE }?.also { this.fiatCurrency = it }
            orRequest.cryptoCurrency.takeIf { it != CpmkCryptoCurrency.NONE }?.also { this.cryptoCurrency = it }
            orRequest.action.takeIf { it != CpmkAction.NONE }?.also { this.action = it }
        }
        orResponse = stub
    }
}
