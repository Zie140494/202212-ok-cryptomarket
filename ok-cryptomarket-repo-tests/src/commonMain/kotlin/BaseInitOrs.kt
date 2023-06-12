package ru.otus.otuskotlin.cryptomarket.backend.repo.tests

import ru.otus.otuskotlin.cryptomarket.common.models.*

abstract class BaseInitOrs(val op: String): IInitObjects<CpmkOr> {
    open val lockOld: CpmkOrLock = CpmkOrLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: CpmkOrLock = CpmkOrLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: CpmkUserId = CpmkUserId("owner-123"),
        action: CpmkAction = CpmkAction.BUY,
        lock: CpmkOrLock = lockOld,
    ) = CpmkOr(
        id = CpmkOrId("or-repo-$op-$suf"),
        walletNumber = "$suf stub",
        accountNumber = "$suf stub accountNumber",
        ownerId = ownerId,
        fiatCurrency = CpmkFiatCurrency.RUB,
        cryptoCurrency = CpmkCryptoCurrency.BTC,
        action = action,
        lock = lock,
    )
}
