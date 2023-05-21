package ru.otus.otuskotlin.cryptomarket.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cryptomarket.common.NONE
import ru.otus.otuskotlin.cryptomarket.common.statemachine.SMOrStates

data class CpmkOr(
    var id: CpmkOrId = CpmkOrId.NONE,
    var accountNumber: String = "",
    var walletNumber: String = "",
    var fiatCurrency: CpmkFiatCurrency = CpmkFiatCurrency.NONE,
    var cryptoCurrency: CpmkCryptoCurrency = CpmkCryptoCurrency.NONE,
    var action: CpmkAction = CpmkAction.NONE,
    var orState: SMOrStates = SMOrStates.NONE,
    var dollarEqualent: Float = 0F,
    var timeCreated: Instant = Instant.NONE,
    var timeUpdated: Instant = Instant.NONE,
    var value: Float = 0F,
    var ownerId: CpmkUserId = CpmkUserId.NONE,
    val permissionsClient: MutableSet<CpmkOrPermissionClient> = mutableSetOf()
){
    fun deepCopy(): CpmkOr = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )
}
