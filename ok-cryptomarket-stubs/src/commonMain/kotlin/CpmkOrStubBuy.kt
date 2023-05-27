package ru.otus.otuskotlin.cryptomarket.stubs

import ru.otus.otuskotlin.cryptomarket.common.models.*

object CpmkOrStubBuy {
    val OR_BUY: CpmkOr
        get() = CpmkOr(
            id = CpmkOrId("666"),
           accountNumber = "accNum crypto 1",
            walletNumber = "walNum crypto 1",
            fiatCurrency = CpmkFiatCurrency.NONE,
            cryptoCurrency = CpmkCryptoCurrency.NONE,
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
