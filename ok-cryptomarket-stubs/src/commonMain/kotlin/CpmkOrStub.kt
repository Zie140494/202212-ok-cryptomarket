package ru.otus.otuskotlin.cryptomarket.stubs

import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.stubs.CpmkOrStubBuy.OR_BUY1

object CpmkOrStub {
    fun get(): CpmkOr = OR_BUY1.copy()

    fun prepareResult(block: CpmkOr.() -> Unit): CpmkOr = get().apply(block)

    fun prepareSearchList(filter: String, type: CpmkAction) = listOf(
        cpmkOrDemand("1", filter, type),
        cpmkOrDemand("2", filter, type),
        cpmkOrDemand("3", filter, type),
        cpmkOrDemand("4", filter, type),
        cpmkOrDemand("5", filter, type),
        cpmkOrDemand("6", filter, type),
    )

    private fun cpmkOrDemand(id: String, filter: String, action: CpmkAction) =
        cpmkOr(get(), id = id, filter = filter, action = action)

    private fun cpmkOr(base: CpmkOr, id: String, filter: String, action: CpmkAction) = base.copy(
        id = CpmkOrId(id),
        walletNumber = "walNum $filter $id",
        accountNumber = "accNum $filter $id",
        fiatCurrency = CpmkFiatCurrency.NONE,
        cryptoCurrency = CpmkCryptoCurrency.NONE,
        action = action
    )

}
