package ru.otus.otuskotlin.cryptomarket.backend.repository.inmemory.model

import ru.otus.otuskotlin.cryptomarket.common.models.*

data class OrEntity(
    val id: String? = null,
    val walletNumber: String? = null,
    val accountNumber: String? = null,
    val value: String? = null,
    val ownerId: String? = null,
    val fiatCurrency: String? = null,
    val cryptoCurrency: String? = null,
    val action: String? = null,
    val lock: String? = null,
) {
    constructor(model: CpmkOr): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        walletNumber = model.walletNumber.takeIf { it.isNotBlank() },
        accountNumber = model.accountNumber.takeIf { it.isNotBlank() },
        value = model.value.takeIf { it >= 0F }.toString(),
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        fiatCurrency = model.fiatCurrency.takeIf { it != CpmkFiatCurrency.NONE }?.name,
        cryptoCurrency = model.cryptoCurrency.takeIf { it != CpmkCryptoCurrency.NONE }?.name,
        action = model.action.takeIf { it != CpmkAction.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = CpmkOr(
        id = id?.let { CpmkOrId(it) }?: CpmkOrId.NONE,
        walletNumber = walletNumber?: "",
        accountNumber = accountNumber?: "",
        value = value!!.toFloat(),
        ownerId = ownerId?.let { CpmkUserId(it) }?: CpmkUserId.NONE,
        fiatCurrency = fiatCurrency?.let { CpmkFiatCurrency.valueOf(it) }?: CpmkFiatCurrency.NONE,
        cryptoCurrency = cryptoCurrency?.let { CpmkCryptoCurrency.valueOf(it) }?: CpmkCryptoCurrency.NONE,
        action = action?.let { CpmkAction.valueOf(it) }?: CpmkAction.NONE,
        lock = lock?.let { CpmkOrLock(it) } ?: CpmkOrLock.NONE,
    )
}
