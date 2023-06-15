package ru.otus.otuskotlin.cryptomarket.common.repo

import ru.otus.otuskotlin.cryptomarket.common.models.CpmkAction
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkUserId

data class DbOrFilterRequest(
    val walletNumberFilter: String = "",
    val ownerId: CpmkUserId = CpmkUserId.NONE,
    val action: CpmkAction = CpmkAction.NONE,
)
