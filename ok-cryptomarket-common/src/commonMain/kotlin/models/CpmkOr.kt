package ru.otus.otuskotlin.cryptomarket.common.models

data class CpmkOr(
    var id: CpmkOrId = CpmkOrId.NONE,
    var accountNumber: String = "",
    var walletNumber: String = "",
    var fiatCurrency: CpmkFiatCurrency = CpmkFiatCurrency.NONE,
    var cryptoCurrency: CpmkCryptoCurrency = CpmkCryptoCurrency.NONE,
    var action: CpmkAction = CpmkAction.NONE,
    var ownerId: CpmkUserId = CpmkUserId.NONE,
    val permissionsClient: MutableSet<CpmkOrPermissionClient> = mutableSetOf()
)
