package ru.otus.otuskotlin.cryptomarket.springapp.api.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.cryptomarket.api.models.*
import ru.otus.otuskotlin.cryptomarket.biz.CpmkOrProcessor
import ru.otus.otuskotlin.cryptomarket.common.models.CpmkCommand
import ru.otus.otuskotlin.cryptomarket.logging.common.CmLoggerProvider
import ru.otus.otuskotlin.cryptomarket.mappers.*


@RestController
@RequestMapping("or")
class OrController
    (
    private val processor: CpmkOrProcessor,
    private val loggerProvider: CmLoggerProvider,
) {
    @PostMapping("create")
    suspend fun createOr(@RequestBody request: OrCreateRequest): OrCreateResponse =
        process(processor, CpmkCommand.CREATE, request = request, loggerProvider.logger(OrController::class), "or-create")

    @PostMapping("read")
    suspend fun  readOr(@RequestBody request: OrReadRequest): OrReadResponse =
        process(processor, CpmkCommand.READ, request = request, loggerProvider.logger(OrController::class), "or-read")

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun  updateOr(@RequestBody request: OrUpdateRequest): OrUpdateResponse =
        process(processor, CpmkCommand.UPDATE, request = request, loggerProvider.logger(OrController::class), "or-update")

    @PostMapping("delete")
    suspend fun  deleteOr(@RequestBody request: OrDeleteRequest): OrDeleteResponse =
        process(processor, CpmkCommand.DELETE, request = request, loggerProvider.logger(OrController::class), "or-delete")

    @PostMapping("search")
    suspend fun  searchOr(@RequestBody request: OrSearchRequest): OrSearchResponse =
        process(processor, CpmkCommand.SEARCH, request = request, loggerProvider.logger(OrController::class), "or-search")
}
