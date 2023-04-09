package ru.otus.otuskotlin.cryptomarket.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cryptomarket.common.models.*
import ru.otus.otuskotlin.cryptomarket.common.stubs.CpmkStubs

data class CpmkContext(
    var command: CpmkCommand = CpmkCommand.NONE,
    var state: CpmkState = CpmkState.NONE,
    val errors: MutableList<CpmkError> = mutableListOf(),

    var workMode: CpmkWorkMode = CpmkWorkMode.PROD,
    var stubCase: CpmkStubs = CpmkStubs.NONE,

    var requestId: CpmkRequestId = CpmkRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var orRequest: CpmkOr = CpmkOr(),
    var orFilterRequest: CpmkOrFilter = CpmkOrFilter(),
    var orResponse: CpmkOr = CpmkOr(),
    var orsResponse: MutableList<CpmkOr> = mutableListOf(),
)
