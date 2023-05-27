package ru.otus.otuskotlin.cryptomarket.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class CmLoggerProvider(
    private val provider: (String) -> ICmLogWrapper = { ICmLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    fun logger(function: KFunction<*>) = provider(function.name)
}
