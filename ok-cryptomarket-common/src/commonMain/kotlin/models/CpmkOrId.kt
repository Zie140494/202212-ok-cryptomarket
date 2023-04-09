package ru.otus.otuskotlin.cryptomarket.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CpmkOrId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CpmkOrId("")
    }
}
