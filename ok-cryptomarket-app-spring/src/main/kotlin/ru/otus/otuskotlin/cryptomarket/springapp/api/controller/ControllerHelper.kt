package ru.otus.otuskotlin.cryptomarket.springapp.api.controller

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.cryptomarket.api.logs.mapper.toLog
import ru.otus.otuskotlin.cryptomarket.api.models.IRequest
import ru.otus.otuskotlin.cryptomarket.api.models.IResponse
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.CpmkContext
import ru.otus.otuskotlin.cryptomarket.common.helpers.asCpmkError
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkState
import ru.otus.otuskotlin.cryptomarket.logging.common.ICmLogWrapper
import ru.otus.otuskotlin.cryptomarket.mappers.fromTransport
import ru.otus.otuskotlin.cryptomarket.mappers.toTransportOr


suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
    processor: CpmkOrProcessor,
    command: CpmkCommand? = null,
    request: Q,
    logger: ICmLogWrapper,
    logId: String,
): R {
    val ctx = CpmkContext(
        timeStart = Clock.System.now(),
    )
    return try {
        logger.doWithLogging(id = logId) {
            ctx.fromTransport(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            ctx.toTransportOr() as R
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
            )
            ctx.state = CpmkState.FAILING
            ctx.errors.add(e.asCpmkError())
            processor.exec(ctx)
            ctx.toTransportOr() as R
        }
    }
}
