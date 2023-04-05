package ru.otus.otuskotlin.cryptomarket.common.models

data class CpmkError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
