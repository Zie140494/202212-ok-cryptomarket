package ru.otus.otuskotlin.cryptomarket.common

import ru.otus.otuskotlin.cryptomarket.logging.common.CmLoggerProvider

data class CpmkCorSettings(
    val loggerProvider: CmLoggerProvider = CmLoggerProvider(),
) {
    companion object {
        val NONE = CpmkCorSettings()
    }
}
