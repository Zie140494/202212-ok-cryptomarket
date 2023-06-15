package ru.otus.otuskotlin.cryptomarket.common.repo

import ru.otus.otuskotlin.cryptomarket.common.models.CpmkError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<CpmkError>
}
