package ru.otus.otuskotlin.cryptomarket.stubs

import ru.otus.otuskotlin.cryptomarket.common.models.*

object CpmkOrStubBuy {
    val OR_BUY: CpmkOr
        get() = CpmkOr(
            id = CpmkOrId("666"),
           accountNumber = "012345678901234567890",
            walletNumber = "x0-9525847222266666666",
            fiatCurrency = CpmkFiatCurrency.RUB,
            cryptoCurrency = CpmkCryptoCurrency.BTC,
            action = CpmkAction.BUY,
            ownerId = CpmkUserId("user-1"),
            permissionsClient = mutableSetOf(
                CpmkOrPermissionClient.READ,
                CpmkOrPermissionClient.UPDATE,
                CpmkOrPermissionClient.DELETE
            ),
        )
    val OR_BUY1 = OR_BUY.copy(action = CpmkAction.BUY)
}
