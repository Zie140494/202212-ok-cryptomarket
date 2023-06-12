package ru.otus.otuskotlin.cryptomarket.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.cryptomarket.logging.common.ICmLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun cmLoggerLogback(logger: Logger): ICmLogWrapper = CmLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun cmLoggerLogback(clazz: KClass<*>): ICmLogWrapper = cmLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun cmLoggerLogback(loggerId: String): ICmLogWrapper = cmLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
