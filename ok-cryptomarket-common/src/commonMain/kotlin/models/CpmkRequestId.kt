package ru.otus.otuskotlin.cryptomarket.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CpmkRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CpmkRequestId("")
    }
}
