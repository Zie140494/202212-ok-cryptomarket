package ru.otus.otuskotlin.cryptomarket.common.models

data class CpmkOrFilter(
    var searchString: String = "",
    var ownerId: CpmkUserId = CpmkUserId.NONE,
)
